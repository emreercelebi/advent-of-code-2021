rm *.class

for dir in */; do
  cd $dir
  rm *.class
  cd ..
done