variable "region" {
  description = "AWS region"
  default     = "eu-west-3" # Paris
}

variable "environment" {
  description = "Environment (dev, staging, preprod, prod)"
  default     = "dev"
}