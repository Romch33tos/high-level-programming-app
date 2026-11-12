#!/usr/bin/env bash
set -e

cd "$(dirname "$0")/.."

LIB_DIR="lib"
SQL_DIR="scripts/sql"
DATA_DIR="data"

H2_JAR=$(ls "$LIB_DIR"/h2-*.jar | head -n1)

if [ -z "$H2_JAR" ]; then
  echo "ОШИБКА: Не найден h2-*.jar в папке lib/"
  exit 1
fi

mkdir -p "$DATA_DIR"

cat "$SQL_DIR/init.sql" "$SQL_DIR/fill.sql" > "$DATA_DIR/all.sql"

echo "Инициализация базы данных H2..."

java -cp "$H2_JAR" org.h2.tools.RunScript \
  -url "jdbc:h2:./$DATA_DIR/app_db" \
  -user sa \
  -password "" \
  -script "./$DATA_DIR/all.sql"

rm "$DATA_DIR/all.sql"

echo "База данных успешно создана и заполнена: $DATA_DIR/app_db.mv.db"
