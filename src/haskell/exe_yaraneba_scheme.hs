
import System.Process

{-
 -   1.shymotionデータベースを削除
 -     2.shymotionデータベースを作成
 -       3.scalerにshymotionデータベースの操作権限付与
 -        -}

main = do
    system "mysql -u root -e \"DROP DATABASE IF EXISTS yaraneba;\""
    system "mysql -u root < ~/various/scala_spray/todolist/src/haskell/exe_yaraneba_scheme.sql"
    system "mysql -u root -e \"GRANT ALL PRIVILEGES ON yaraneba.* TO scaler@localhost;\""
