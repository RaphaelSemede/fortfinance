-- Excluir a tabela (irá remover os dados)
DROP TABLE usuario CASCADE CONSTRAINTS;

-- Excluir a sequência
DROP SEQUENCE seq_usuario_id;

-- Agora recrie tudo do zero:
-- Criação da tabela
CREATE TABLE usuario (
    id NUMBER PRIMARY KEY,
    nome_completo VARCHAR2(200) NOT NULL,
    username VARCHAR2(100) NOT NULL UNIQUE,
    email VARCHAR2(50) NOT NULL UNIQUE,
    senha VARCHAR2(100) NOT NULL,
    telefone VARCHAR2(15) NOT NULL
);

-- Criação da sequência
CREATE SEQUENCE seq_usuario_id
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- Gatilho
CREATE OR REPLACE TRIGGER trg_usuario_id
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
    SELECT seq_usuario_id.NEXTVAL INTO :NEW.id FROM dual;
END;
/

-- Inserção (com username e email diferentes)
INSERT INTO usuario (
    nome_completo, username, email, senha, telefone
) VALUES (
    'Raphael', 'raphael_02', 'raphael02@hotmail.com', '186078', '11916822805'
);

-- Consulta
SELECT * FROM usuario;
