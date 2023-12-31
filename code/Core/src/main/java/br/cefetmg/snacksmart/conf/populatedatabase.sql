USE `snacksmart`;

-- --------------------------------------------------------

-- 
-- Inserção de dados na tabela gerente
-- 

INSERT INTO `gerente`(
    `nome`,
    `senha`,
    `cpf`,
    `telefone`,
    `email`
)
VALUES(
    'Gerente',
    'F59EFF44A211FA33E2513758C4C3BB13582678C5FF8B64D6C6338A0C6512A4AD', -- ger
    '000.000.000-00',
    '(00)999999999',
    'gerente.desenvolvimento@dev.mail'
);

-- --------------------------------------------------------

-- 
-- Inserção de dados na tabela locatario
-- 

INSERT INTO `locatario`(
    `nome`,
    `senha`,
    `cpf`,
    `telefone`,
    `email`
)
VALUES(
    'Locatario de desenvolvimento',
    'A7F32380CF333659452317BD08B287067AC939609B81D093391A047818A38960', -- loc
    '111.111.111-11',
    '(00)111111111',
    'locatario.desenvolvimento@dev.mail'
);

INSERT INTO `locatario`(
    `nome`,
    `senha`,
    `cpf`,
    `telefone`,
    `email`
)
VALUES(
    'Locatario de desenvolvimento 2',
    'B854FDBC58E2AAE8FDF4022A2FCC560D9187982E250E9F25075A70D0919BFE77', -- loc2
    '222.222.222-22',
    '(00)222222222',
    'locatario.desenvolvimento2@dev.mail'
);

-- --------------------------------------------------------

-- 
-- Inserção de dados na tabela maquina
-- 

INSERT INTO `maquina` (
    `nome`,
    `codigo`, 
    `localizacao`,
    `aluguel`,
    `status`,
    `tipo`,
    `locatario__fk`
)
VALUES (
    'maq dev 1',
    '1',
    'Rua Geólogo Thomaz de Aquino, Itapoã, Belo Horizonte, MG - 31710-680',
    '30.91',
    'ALUGADA',
    'REFRIGERADA',
    '2'
);

INSERT INTO `maquina` (
    `nome`,
    `codigo`, 
    `localizacao`,
    `aluguel`,
    `status`,
    `tipo`,
    `locatario__fk`    
)
VALUES (
    'maq dev 2',
    '2',
    'Rua Walter Guimarães Figueiredo, Buritis, Belo Horizonte, MG - 30492-030',
    '30.92',
    'EM_MANUTENCAO',
    'REFRIGERADA',
    '1'
);

INSERT INTO `maquina` (
    `nome`,
    `codigo`, 
    `localizacao`,
    `aluguel`,
    `status`,
    `tipo`,
    `locatario__fk`    
)
VALUES (
    'maq dev 3',
    '3',
    'Rua Radialista Ortiz Coelho, Céu Azul, Belo Horizonte, MG - 31580-340',
    '30.93',
    'ALUGADA',
    'NAO_REFRIGERADA',
    '1'
);


INSERT INTO `maquina`(
    `nome`,
    `codigo`, 
    `localizacao`,
    `aluguel`,
    `status`,
    `tipo`,
    `locatario__fk`    
)
VALUES (
    'maq dev 4',
    '4',
    'Rua Corumbá, Carlos Prates, Belo Horizonte, MG - 30710-280',
    '30.94',
    'DISPONIVEL',
    'REFRIGERADA',
    '2'
);


-- --------------------------------------------------------

-- 
-- Inserção de dados na tabela contrato
-- 

INSERT INTO `contrato`(
    `observacoes`,
    `data_inicio`,
    `data_fim`,
    `data_pagamento`,
    `valor`,
    `gerente__fk`,
    `locatario__fk`,
    `maquina__fk`,
    `estado`
) 
VALUES (
    'contrato para desenvolvimento 1',
    '2023-06-01',
    '2023-12-01',
    '2023-06-08',
    '66.8',
    '1',
    '1',
    '1',
    'VIGENTE'
);

INSERT INTO `contrato`(
    `observacoes`,
    `data_inicio`,
    `data_fim`,
    `data_pagamento`,
    `valor`,
    `gerente__fk`,
    `locatario__fk`,
    `maquina__fk`,
    `estado`
) 
VALUES (
    'contrato para desenvolvimento 2',
    '2023-08-08',
    '2024-01-01',
    '2023-08-15',
    '50.8',
    '1',
    '1',
    '2',
    'VIGENTE'
);

INSERT INTO `contrato`(
    `observacoes`,
    `data_inicio`,
    `data_fim`,
    `data_pagamento`,
    `valor`,
    `gerente__fk`,
    `locatario__fk`,
    `maquina__fk`,
    `estado`
) 
VALUES (
    'contrato para desenvolvimento 3',
    '2023-07-01',
    '2024-10-01',
    '2023-07-08',
    '420',
    '1',
    '2',
    '3',
    'VIGENTE'
);

INSERT INTO `contrato`(
    `observacoes`,
    `data_inicio`,
    `data_fim`,
    `data_pagamento`,
    `valor`,
    `gerente__fk`,
    `locatario__fk`,
    `maquina__fk`,
    `estado`
)
VALUES (
    'contrato para desenvolvimento expirado',
    '2023-07-01',
    '2023-10-01',
    '2023-07-08',
    '420',
    '1',
    '2',
    '3',
    'VIGENTE'
);

INSERT INTO `contrato`(
    `observacoes`,
    `data_inicio`,
    `data_fim`,
    `data_pagamento`,
    `valor`,
    `gerente__fk`,
    `locatario__fk`,
    `maquina__fk`,
    `estado`
)
VALUES (
           'contrato para desenvolvimento cancelamento solicitado',
           '2023-07-01',
           '2024-01-01',
           '2023-07-08',
           '320',
           '1',
           '2',
           '3',
           'CANCELAMENTO_SOLICITADO'
       );

INSERT INTO `contrato`(
    `observacoes`,
    `data_inicio`,
    `data_fim`,
    `data_pagamento`,
    `valor`,
    `gerente__fk`,
    `locatario__fk`,
    `maquina__fk`,
    `estado`
) 
VALUES (
    'contrato para desenvolvimento 4',
    '2023-04-04',
    '2023-12-18',
    '2023-04-18',
    '110.99',
    '1',
    '2',
    '3',
    'CANCELADO'
);
