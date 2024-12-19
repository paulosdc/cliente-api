# Cliente API

## Principais Endpoints

### Criar Categoria
```bash
curl --location 'http://localhost:8080/categoria' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Pessoal"
}'
```

### Listar Categorias
```bash
curl --location 'http://localhost:8080/categoria'
```

### Criar Cliente
```bash
curl --location 'http://localhost:8080/cliente' \
--header 'Content-Type: application/json' \
--data-raw '{
    "inscricao": "15.939.758/0001-67",
    "nome": "Paulo",
    "apelido": "Sérgio",
    "status": "ATIVADO",
    "urlFoto": "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/768px-User_icon_2.svg.png",
    "emails": [
        {"categoria": {"id": 1}, "nome": "Meu email pessoal", "email": "paulo@gmail.com"},
        {"categoria": {"id": 2}, "nome": "Meu email de trabalho", "email": "pauloprofissional@gmail.com"}
    ]
}'
```

### Listar Todos os Clientes
```bash
curl --location 'http://localhost:8080/cliente/all'
```


