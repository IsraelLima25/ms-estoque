CREATE TABLE tbl_historico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    tipo_movimentacao VARCHAR(20) NOT NULL,
    numero_nota_fiscal VARCHAR(20) NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (produto_id) REFERENCES tbl_produto(id)
);