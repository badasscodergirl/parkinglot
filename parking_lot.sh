file_name=$1
jar_name="target/parkinglot-1.0.jar"
mvn compile
mvn clean install
if test -z "$file_name"
then
    java -jar $jar_name
else 
   java -jar $jar_name $file_name
fi

