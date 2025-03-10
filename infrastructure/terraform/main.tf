terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = " 5.0.0"  # Une version compatible avec les modules actuels
    }
  }

  backend "s3" {
    bucket = "gestion-etablissement-terraform-state"
    key    = "state/terraform.tfstate"
    region = "eu-west-3"
    endpoint = "http://host.docker.internal:9900"
    force_path_style = true
    skip_credentials_validation = true
    skip_metadata_api_check = true
    skip_region_validation = true
    access_key = "minioadmin"
    secret_key = "minioadmin"
  }
}

provider "aws" {
  region = var.region
}

# Utiliser une version spécifique du module VPC
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "4.0.0"  # Version mise à jour pour éviter les problèmes de compatibilité

  name = "gestion-etablissement-vpc"
  cidr = "10.0.0.0/16"

  azs             = ["${var.region}a", "${var.region}b", "${var.region}c"]
  private_subnets = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  public_subnets  = ["10.0.101.0/24", "10.0.102.0/24", "10.0.103.0/24"]

  enable_nat_gateway = true
  single_nat_gateway = true

  tags = {
    Environment = var.environment
    Project     = "gestion-etablissement"
  }
}

# Rôle IAM pour EKS
resource "aws_iam_role" "eks_cluster" {
  name = "eks-cluster-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "eks.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "eks_cluster_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.eks_cluster.name
}

# Création du cluster EKS
resource "aws_eks_cluster" "main" {
  name     = "gestion-etablissement-${var.environment}"
  role_arn = aws_iam_role.eks_cluster.arn
  version  = "1.25"

  vpc_config {
    subnet_ids = module.vpc.private_subnets
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_cluster_policy
  ]
}

# Rôle IAM pour les nœuds
resource "aws_iam_role" "eks_nodes" {
  name = "eks-node-group-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "eks_worker_node_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
  role       = aws_iam_role.eks_nodes.name
}

resource "aws_iam_role_policy_attachment" "eks_cni_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
  role       = aws_iam_role.eks_nodes.name
}

resource "aws_iam_role_policy_attachment" "eks_container_registry" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  role       = aws_iam_role.eks_nodes.name
}

# Groupe de nœuds EKS
resource "aws_eks_node_group" "main" {
  cluster_name    = aws_eks_cluster.main.name
  node_group_name = "gestion-etablissement-nodes"
  node_role_arn   = aws_iam_role.eks_nodes.arn
  subnet_ids      = module.vpc.private_subnets

  scaling_config {
    desired_size = 2
    max_size     = 3
    min_size     = 1
  }

  instance_types = ["t3.medium"]

  depends_on = [
    aws_iam_role_policy_attachment.eks_worker_node_policy,
    aws_iam_role_policy_attachment.eks_cni_policy,
    aws_iam_role_policy_attachment.eks_container_registry,
  ]
}

# ECR Repository pour chaque microservice
resource "aws_ecr_repository" "microservices" {
  for_each = toset([
    "classe-service",
    "cours-service",
    "etudiant-service",
    "professeur-service",
    "emploi-service",
    "gateway-service",
    "config-server",
    "discovery-service"
  ])

  name                 = each.key
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}