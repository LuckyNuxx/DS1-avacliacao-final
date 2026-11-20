#!/bin/bash
# Compila o projeto e executa cada teste, salvando a saída em testes/saidas/.
# Uso (na raiz do projeto): bash testes/executar-testes.sh
set -e
cd "$(dirname "$0")/.."
mkdir -p out testes/saidas
javac -encoding UTF-8 -d out src/*.java
for entrada in testes/entradas/*.txt; do
    nome=$(basename "$entrada" .txt)
    java -Dstdout.encoding=UTF-8 -Dfile.encoding=UTF-8 -cp out Main < "$entrada" > "testes/saidas/$nome.txt"
    echo "Executado: $nome"
done
