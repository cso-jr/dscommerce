# DSCOMMERCE
Plataforma de e-commerce desenvolvida durante o curso Java Professional

## Inclui funcionalidades:
- Cadastro de usuários, produtos e categorias;
- Catálogo de produtos que podem ser filtrados pelo nome;
- A partir do catálogo, o produto pode ser selecionado para ver detalhes e inclusão no carrinho de compras;
- É possível  incluir ou remover do carrinho e alterar quantidades de cada item;
- Ao encerrar o pedido, ele é salvo com status "aguardando pagamento";
- Ao realizar o pagamento, o instante é registrado;
- usuários podem ser clientes ou administradores

## Recursos, Tecnologias, Ferramentas e Técnicas Utilizadas:
- Spring Boot Framework, utilizando Maven como gerenciador de dependências;
- Banco de dados H2 para teste e PostgreSQL para desenvolvimento;
- Postman para teste dos endpoints com resposta em formato JSON;
- Autenticação de usuário com JWT;
- Modelo de domínio com relacionamentos muitos-para-muitos, muitos-para-um;
- CRUD de categorias, produtos e usuários;
- Usuários com um ou mais "Roles" (client, admin) como perfil de acesso;
- Captura de exceções customizadas;
- Desenvolvimento com padrão em camadas MVC;
- Validação de dados;
- Consultas com SQL e JPQL;

## Diagrama:
<img width="891" height="438" alt="dscommerceDiagram" src="https://github.com/user-attachments/assets/69425206-336c-4dc6-814f-7d36228e1bd1" />

## Resposta de consulta a um pedido (orderItem) do próprio usuário logado:
<img width="1504" height="917" alt="orderItem" src="https://github.com/user-attachments/assets/8b9d0192-2b7f-4f26-a16a-838a0d9131b1" />
