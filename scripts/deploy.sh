LAMBDA_FOLDER=$1
LAMBDA_MODULE=$2

. build.sh $LAMBDA_FOLDER $LAMBDA_MODULE

aws lambda update-function-code --function-name $LAMBDA_FOLDER --zip-file fileb://$LAMBDA_FOLDER.zip --profile sovos-gvat-dev
rm $LAMBDA_FOLDER.zip