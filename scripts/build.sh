LAMBDA_FOLDER=$1
LAMBDA_MODULE=$2

cd ../lambdas/$LAMBDA_FOLDER/.venv/Lib/site-packages
zip -r9 ${OLDPWD}/$LAMBDA_FOLDER.zip .

cd $OLDPWD
cp ../lambdas/$LAMBDA_FOLDER/src/$LAMBDA_MODULE.py .
zip -u $LAMBDA_FOLDER.zip $LAMBDA_MODULE.py
rm $LAMBDA_MODULE.py