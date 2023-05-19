# Configure the AWS Provider
provider "aws" {
  region              = "REPLACE-WITH-REGION"
  allowed_account_ids = ["REPLACE_ACC_ID"]
  profile             = "REPLACE-WITH-PROFILE-NAME"
}