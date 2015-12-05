## Yaraneba

Make a TODO List with my Friend

create MySQL user scaler / scaler
create Mac directory ~/various/scala_spray/

0. move directory & create MySQL user

        $ cd ~/various/scala_spray/
        $ "mysql -u root -e "CREATE USER 'scaler' IDENTIFIED BY 'scaler';"

1. Git-clone this repository.

        $ git clone git://github.com/spray/spray-template.git todolist

2. Change directory into your clone:

        $ cd todolist

3. Setuo the database

        $ runghc exe_yaraneba_scheme.hs

4. Launch SBT:

        $ sbt

5. Compile everything and run all tests:

        > test

6. Start the application:

        > re-start

7. Browse to [http://127.0.0.1:650001](http://127.0.0.1:650001/)

8. Stop the application:

        > re-stop

