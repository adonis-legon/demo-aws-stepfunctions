LAMBDA_FOLDER=$1
LAMBDA_MODULE=$2
AWS_PROFILE=$3

. build.sh $LAMBDA_FOLDER $LAMBDA_MODULE

aws lambda update-function-code --function-name $LAMBDA_FOLDER --zip-file fileb://$LAMBDA_FOLDER.zip --profile $AWS_PROFILE
rm $LAMBDA_FOLDER.zip