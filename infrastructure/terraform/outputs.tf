output "cluster_endpoint" {
  description = "EKS cluster endpoint"
  value       = aws_eks_cluster.main.endpoint
}

output "cluster_name" {
  description = "EKS cluster name"
  value       = aws_eks_cluster.main.name
}

output "ecr_repository_urls" {
  description = "ECR Repository URLs"
  value       = { for name, repo in aws_ecr_repository.microservices : name => repo.repository_url }
}