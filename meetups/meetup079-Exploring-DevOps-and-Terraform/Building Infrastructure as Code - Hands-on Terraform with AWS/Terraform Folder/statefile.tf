terraform {
  backend "s3" {
    bucket  = "REPLACE-WITH-S3-BUCKET-NAME"
    key     = "REPLACE/WITH/KEY/PATH/A_NAME.tfstate"
    region  = "REPLACE-WITH-REGION"
    profile = "REPLACE-WITH-PROFILE-NAME"
    encrypt = true
  }
}