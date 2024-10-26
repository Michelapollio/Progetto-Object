DROP SCHEMA IF EXISTS s1 CASCADE;
CREATE SCHEMA s1;

CREATE TYPE stato AS ENUM ('privato', 'pubblico', 'deleted');

CREATE TABLE s1.Utente
(
    IdUtente SERIAL,
    Nome     VARCHAR(50) NOT NULL,
    Cognome  VARCHAR(50) NOT NULL,
    Email    VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Utente PRIMARY KEY (IdUtente)
);

CREATE TABLE s1.Dispostivo
(
    IdDispositivo SERIAL,
    Tipologia     VARCHAR(50) NOT NULL,
    IdUtente      INTEGER     NOT NULL,
    CONSTRAINT PK_Dispositivo PRIMARY KEY (IdDispositivo),
    CONSTRAINT FK_Dispositivo_Utente FOREIGN KEY (IdUtente) REFERENCES s1.Utente (IdUtente)
);

CREATE TABLE s1.Foto
(
    IdFoto        SERIAL,
    datascatto    date,
    Stato         stato,
    IdUtente      INTEGER NOT NULL,
    IdDispositivo INTEGER NOT NULL,
    CONSTRAINT PK_Foto PRIMARY KEY (IdFoto),
    CONSTRAINT FK_Foto_Utente FOREIGN KEY (IdUtente) REFERENCES s1.Utente (IdUtente),
    CONSTRAINT FK_Foto_Dispositivo FOREIGN KEY (IdDispositivo) REFERENCES s1.Dispostivo (IdDispositivo)
);

CREATE TABLE s1.Luogo
(
    IdLuogo     SERIAL,
    Nome        VARCHAR(50),
    Latitudine  DECIMAL(10, 8) NOT NULL,
    Longitudine DECIMAL(11, 8) NOT NULL,
    CONSTRAINT PK_Luogo PRIMARY KEY (IdLuogo),
    CONSTRAINT UQ_Coordinate UNIQUE (Latitudine, Longitudine),
    CONSTRAINT UQ_Nome UNIQUE (Nome)
);

CREATE TABLE s1.LuogoFoto
(
    IdLuogo INTEGER NOT NULL,
    IdFoto  INTEGER NOT NULL,
    CONSTRAINT PK_LuogoFoto PRIMARY KEY (IdLuogo, IdFoto),
    CONSTRAINT FK_LuogoFoto_Luogo FOREIGN KEY (IdLuogo) REFERENCES s1.Luogo (IdLuogo),
    CONSTRAINT FK_LuogoFoto_Foto FOREIGN KEY (IdFoto) REFERENCES s1.Foto (IdFoto)
);

CREATE TABLE s1.Soggetto
(
    IdSoggetto SERIAL,
    Categoria  VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Soggetto PRIMARY KEY (IdSoggetto)
);

CREATE TABLE s1.SoggettoFoto
(
    IdSoggetto INTEGER NOT NULL,
    IdFoto     INTEGER NOT NULL,
    CONSTRAINT PK_SoggettoFoto PRIMARY KEY (IdSoggetto, IdFoto),
    CONSTRAINT FK_SoggettoFoto_Soggetto FOREIGN KEY (IdSoggetto) REFERENCES s1.Soggetto (IdSoggetto),
    CONSTRAINT FK_SoggettoFoto_Foto FOREIGN KEY (IdFoto) REFERENCES s1.Foto (IdFoto)
);

CREATE TABLE s1.Tag
(
    IdUtente INTEGER NOT NULL,
    IdFoto   INTEGER NOT NULL,
    CONSTRAINT PK_Tag PRIMARY KEY (IdUtente, IdFoto),
    CONSTRAINT FK_Tag_Utente FOREIGN KEY (IdUtente) REFERENCES s1.Utente (IdUtente),
    CONSTRAINT FK_Tag_Foto FOREIGN KEY (IdFoto) REFERENCES s1.Foto (IdFoto)
);

CREATE TABLE s1.Album
(
    IdAlbum       Serial,
    Nome          VARCHAR(50) NOT NULL,
    DataCreazione DATE,
    IdOwner       INTEGER     NOT NULL,
    Privacy       BOOLEAN     NOT NULL,
    CONSTRAINT PK_Album PRIMARY KEY (IdAlbum),
    CONSTRAINT FK_Album_Utente FOREIGN KEY (IdOwner) REFERENCES s1.Utente (IdUtente)
);

CREATE TABLE s1.FotoAlbum
(
    IdFoto  INTEGER NOT NULL,
    IdAlbum INTEGER NOT NULL,
    CONSTRAINT PK_FotoAlbum PRIMARY KEY (IdFoto, IdAlbum),
    CONSTRAINT FK_FotoAlbum_Foto FOREIGN KEY (IdFoto) REFERENCES s1.Foto (IdFoto),
    CONSTRAINT FK_FotoAlbum_Album FOREIGN KEY (IdAlbum) REFERENCES s1.Album (IdAlbum)
);

CREATE TABLE s1.AlbumUtente
(
    IdAlbum  INTEGER NOT NULL,
    IdUtente INTEGER NOT NULL,
    CONSTRAINT PK_AlbumUtente PRIMARY KEY (IdAlbum, IdUtente),
    CONSTRAINT FK_AlbumUtente_Album FOREIGN KEY (IdAlbum) REFERENCES s1.Album (IdAlbum),
    CONSTRAINT FK_AlbumUtente_Utente FOREIGN KEY (IdUtente) REFERENCES s1.Utente (IdUtente)
);

CREATE or replace VIEW Top3LuogiPiùImmortalati AS
(
SELECT l.IdLuogo, l.Nome, l.Latitudine, l.Longitudine, COUNT(*) AS NumeroFoto
FROM s1.Luogo l,
     s1.LuogoFoto lf
WHERE l.IdLuogo = lf.IdLuogo
GROUP BY l.IdLuogo, l.Nome, l.Latitudine, l.Longitudine
ORDER BY NumeroFoto DESC
LIMIT 3);

create or replace function f1() returns trigger as
$$
declare
    nome1 varchar(50);
begin
    nome1 = concat(NEW.nome, ' ', 'album personale');
    insert into s1.album (nome, datacreazione, idowner, privacy)
    values (nome1, CURRENT_TIMESTAMP, NEW.idutente, false);

    return NEW;
END;
$$ language plpgsql;

create trigger t7
    after insert
    on s1.utente
    for each row
execute function f1();

CREATE OR REPLACE FUNCTION add_to_gallery() RETURNS trigger
AS
$$
BEGIN
    INSERT INTO fotoalbum (idalbum, idfoto)
    SELECT idalbum, NEW.idfoto
    FROM album
    WHERE idowner = NEW.idutente;
    RETURN NEW;
END;
$$ language plpgsql;


CREATE OR REPLACE TRIGGER add_to_gallery_trigger
    AFTER INSERT
    ON foto
    for each row
execute function add_to_gallery();

CREATE OR REPLACE FUNCTION s1.getsameplace(luogoid int)
    returns table
            (
                idfoto        integer,
                stato         stato,
                idutente      integer,
                iddispositivo integer
            )
as
$$
begin
    return query select f.idfoto, f.stato, f.idutente, f.iddispositivo
                 from s1.foto f
                          natural join s1.luogofoto lf
                 where lf.IdLuogo = luogoid;
end;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION s1.getsamesubject(soggettoid int)
    returns table
            (
                idfoto        integer,
                stato         stato,
                idutente      integer,
                iddispositivo integer
            )
as
$$
begin
    return query select f.idfoto, f.stato, f.idutente, f.iddispositivo
                 from s1.foto f
                          natural join s1.soggettofoto sf
                 where sf.IdSoggetto = soggettoid;
end;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION s1.getsameuser(utenteid int)
    returns table
            (
                idfoto        integer,
                stato         stato,
                idutente      integer,
                iddispositivo integer
            )
as
$$
begin
    return query select f.idfoto, f.stato, f.idutente, f.iddispositivo
                 from s1.foto f
                          natural join s1.tag t
                 where t.IdUtente = utenteid;
end;
$$ language plpgsql;

create or replace function s1.f2() returns trigger as
$$
declare
    c1 cursor for select idalbum
                  from s1.album a
                  where a.idowner = OLD.idutente
                    and a.privacy = true;
    albumid s1.Album.idalbum%TYPE;

BEGIN
    open c1;
    loop
        fetch c1 into albumid;
        exit when not found;

        delete
        from s1.fotoalbum
        where idalbum = albumid
          and idfoto = OLD.idfoto;

    end loop;
end;
$$ language plpgsql;

create trigger t2
    after update
    on s1.foto
    for each row
execute function s1.f2();

create or replace function s1.add_data() returns trigger as
$$
BEGIN
    new.datacreazione = CURRENT_TIMESTAMP;
    RETURN NEW;
end;
$$ language plpgsql;

create trigger t4
    before insert
    on s1.album
    for each row
execute function s1.add_data();

create or replace function s1.add_datafoto() returns trigger as
$$
BEGIN
    new.datascatto = CURRENT_TIMESTAMP;
    return new;
end;
$$ language plpgsql;

create trigger t5
    before insert
    on s1.foto
    for each row
execute function s1.add_datafoto();

create or replace function s1.add_utentealbum() returns trigger as
$$
BEGIN
    insert into s1.albumutente(idalbum, idutente)
    VALUES (NEW.idalbum, NEW.idowner);
    return new;
end;
$$ language plpgsql;

create trigger t5
    after insert
    on s1.album
    for each row
execute function s1.add_utentealbum();

insert into s1.utente (nome, cognome, email, password)
values ('Elisa', 'Tiberio', 'elisatiberio@live.it', 'nene26'),
       ('Michela', 'Pollio', 'michelapollio19@icloud.com', 'baldusie19');


insert into s1.album(nome, idowner, privacy)
VALUES ('Praia', 1, true);

insert into s1.dispostivo(tipologia, idutente)
VALUES ('iphone', 2);

insert into s1.foto(stato, idutente, iddispositivo)
VALUES ('privato', 2, 1);

insert into Luogo(nome, latitudine, longitudine)
VALUES ('Complesso Studi Montesant’angelo', 40.839026294160135, 14.184969766924183),
       ('Stadio Diego Armando Maradona', 40.828123191020495, 14.193061095758704),
       ('O’Murzillo', 40.8269492735944, 14.1956988245939),
       ('Piazza Plebiscito', 40.83597168245228, 14.248550909253588),
       (null, 40.838643293527596, 14.252676480418668),
       ('Galleria Umberto Primo ', 40.83847283745015, 14.249436372193905),
       ('Piazza di Spagna', 41.90584948385739, 12.482326995819047),
       (null, 41.89929104233426, 12.473192213005643),
       ('Duomo di Milano', 45.464217966695536, 9.191969411368579),
       ('Duel Club', 40.828068108592305, 14.155206995758762),
       ('Piazza Tasso', 40.626241621412944, 14.37565197856061),
       ('Katarì beach lounge bar', 40.63883580921175, 14.39993868410017),
       ('Palazzo di Schönbrum', 48.18595543300895, 16.3126888943512),
       (null, 35.88852486514128, 14.40577501577596),
       (null, 40.83679554625882, 14.189438780418527);

insert into Utente(nome, cognome, email, password)
VALUES ('Mario', 'Rossi', 'mario@email.com', 'password123'),
       ('Luca', 'Bianchi', 'luca@email.com', 'securepassword'),
       ('giovanni', 'fiume', 'giovannifiume2014@libero.it', 'fiume'),
       ('andrea', 'dota', 'andreadota2000@gmail.com', 'dota'),
       ('alan', 'autorino', 'alan_autorino@hotmail.it', 'autorino'),
       ('emmanuel', 'manna', 'manumanna99@gmail.com', 'manna'),
       ('ilaria', 'gilardi', 'ilariag@live.it', 'gilardi'),
       ('ilaria', 'risimini', 'ilariarisimini@hotmail.it', 'risimini'),
       ('andrea', 'tiberio', 'a.tiberio@gmail.com', 'tibe'),
       ('noemi', 'spera', 'noemis@gmail.com', 'spera'),
       ('rosa', 'liguori', 'rosaliguori05@live.it', 'liguori'),
       ('lisa', 'liguori', 'lisaliguori04@hotmail.it', 'liguori2'),
       ('angela', 'pollio', 'angelapollio@live.it', 'pollio'),
       ('antonio', 'sisimbro', 'tonysisi@hotmail.it', 'sisimbro'),
       ('lorenzo', 'tecchia', 'thewatcher@live.it', 'tecchia'),
       ('tipo', 'frizzantino', 'tipofrizz@icloud.com', 'frizzantino'),
       ('giovanni', 'zampetti', 'algebra.9@icloud.com', 'zampetti'),
       ('alfredo', 'top', 'alfredotop@live.it', 'top'),
       ('francesco', 'ilgemello', 'frageme00@hotmail.it', 'ilgemello'),
       ('marco', 'pastore', 'pastorepecora@outlook.it', 'pastore'),
       ('sabrina', 'amicamichela', 'sabri2001@gmail.com', 'amicamichela'),
       ('alessandro', 'rossi', 'alerossi@libero.it', 'rossi'),
       ('francesca', 'ferrari', 'fraferrari@libero.it', 'ferrari'),
       ('matteo', 'spavone', 'matteospav1@outlook.it', 'spavone'),
       ('sofia', 'bianchi', 'sofiabianchi99@hotmail.it', 'bianchi'),
       ('giulia', 'gallo', 'g.gallo@libero.it', 'gallo'),
       ('giulia', 'conti', 'giualiaconti@live.it', 'conti'),
       ('leonardo', 'marino', 'leomarino@live.it', 'marino'),
       ('emma', 'de luca', 'emmadeluca@libero.it', 'deluca'),
       ('gabriele', 'esposito', 'gabriespo@libero.it', 'esposito'),
       ('chiara', 'rizzo', 'chiararizzo@outlook.it', 'rizzo');



insert into album(nome, idowner, privacy)
VALUES ('Roma', 28, true),
       ('Padova2021', 18, false),
       ('Galleria Univerità', 29, true),
       ('Vacanza a Pestum', 18, true),
       ('Milano2022', 7, false),
       ('Gita a Vienna', 12, true),
       ('Londra', 30, false),
       ('Barcellona', 9, true),
       ('Amsterdam', 20, false),
       ('MSCGrandiosa', 18, true),
       ('Madrid', 6, false),
       ('Estate a Diamante', 14, true),
       ('Atene', 15, true),
       ('Partita16/05', 32, false);


insert into s1.albumutente(idalbum, idutente)
VALUES (3, 2),
       (3, 5);

insert into dispostivo(tipologia, idutente)
VALUES ('macchina fotografica', 15),
       ('cellulare', 19),
       ('cellulare', 16),
       ('cellulare', 20),
       ('cellulare', 18),
       ('cellulare', 29),
       ('macchina fotografica', 28),
       ('macchina fotografica', 20),
       ('macchina fotografica', 17),
       ('macchina fotografica', 27),
       ('ipad', 33),
       ('ipad', 45),
       ('ipad', 43);

INSERT INTO s1.foto(idutente, stato, iddispositivo)
VALUES (21, 'pubblico', 14),
       (12, 'privato', 6),
       (13, 'pubblico', 6),
       (14, 'privato', 3),
       (15, 'pubblico', 2),
       (16, 'privato', 2),
       (17, 'pubblico', 1),
       (18, 'privato', 5),
       (19, 'pubblico', 6),
       (20, 'privato', 7),
       (21, 'pubblico', 8),
       (22, 'privato', 9),
       (23, 'pubblico', 4),
       (24, 'privato', 2),
       (25, 'pubblico', 9),
       (26, 'privato', 3),
       (27, 'pubblico', 1),
       (28, 'privato', 3),
       (29, 'pubblico', 3),
       (30, 'privato', 14);


insert into s1.luogofoto(idluogo, idfoto)
VALUES (16, 15),
       (1, 21),
       (1, 26),
       (1, 29),
       (5, 19),
       (1, 6),
       (4, 30),
       (4, 5),
       (2, 7),
       (1, 3),
       (4, 1),
       (2, 23);

insert into s1.soggetto (categoria)
values ('ritratti'),
       ('natura'),
       ('paesaggi'),
       ('persone'),
       ('animali'),
       ('architettura'),
       ('cibo');


insert into utente (nome, cognome, email, password)
values ('Elisa', 'Tiberio', 'elisatiberio@live.it', 'nene26'),
       ('Michela', 'Pollio', 'michelapollio19@icloud.com', 'baldusie19');


insert into album(nome, idowner, privacy)
VALUES ('Praia', 6, true),
       ('Album 1', 6, false);


insert into utente (nome, cognome, email, password)
values ('Angela', 'Pollio', 'angelapollio@gmail.com', '12345');


insert into Utente(nome, cognome, email, password)
VALUES ('Mario', 'Rossi', 'mario@email.com', 'password123'),
       ('Luca', 'Bianchi', 'luca@email.com', 'securepassword'),
       ('giovanni', 'fiume', 'giovannifiume2014@libero.it', 'fiume'),
       ('andrea', 'dota', 'andreadota2000@gmail.com', 'dota'),
       ('alan', 'autorino', 'alan_autorino@hotmail.it', 'autorino'),
       ('emmanuel', 'manna', 'manumanna99@gmail.com', 'manna'),
       ('ilaria', 'gilardi', 'ilariag@live.it', 'gilardi'),
       ('ilaria', 'risimini', 'ilariarisimini@hotmail.it', 'risimini'),
       ('andrea', 'tiberio', 'a.tiberio@gmail.com', 'tibe'),
       ('noemi', 'spera', 'noemis@gmail.com', 'spera'),
       ('rosa', 'liguori', 'rosaliguori05@live.it', 'liguori'),
       ('lisa', 'liguori', 'lisaliguori04@hotmail.it', 'liguori2'),
       ('angela', 'pollio', 'angelapollio@live.it', 'pollio'),
       ('antonio', 'sisimbro', 'tonysisi@hotmail.it', 'sisimbro'),
       ('lorenzo', 'tecchia', 'thewatcher@live.it', 'tecchia'),
       ('tipo', 'frizzantino', 'tipofrizz@icloud.com', 'frizzantino'),
       ('giovanni', 'zampetti', 'algebra.9@icloud.com', 'zampetti'),
       ('alfredo', 'top', 'alfredotop@live.it', 'top'),
       ('francesco', 'ilgemello', 'frageme00@hotmail.it', 'ilgemello'),
       ('marco', 'pastore', 'pastorepecora@outlook.it', 'pastore'),
       ('sabrina', 'amicamichela', 'sabri2001@gmail.com', 'amicamichela'),
       ('alessandro', 'rossi', 'alerossi@libero.it', 'rossi'),
       ('francesca', 'ferrari', 'fraferrari@libero.it', 'ferrari'),
       ('matteo', 'spavone', 'matteospav1@outlook.it', 'spavone'),
       ('sofia', 'bianchi', 'sofiabianchi99@hotmail.it', 'bianchi'),
       ('giulia', 'gallo', 'g.gallo@libero.it', 'gallo'),
       ('giulia', 'conti', 'giualiaconti@live.it', 'conti'),
       ('leonardo', 'marino', 'leomarino@live.it', 'marino'),
       ('emma', 'de luca', 'emmadeluca@libero.it', 'deluca'),
       ('gabriele', 'esposito', 'gabriespo@libero.it', 'esposito'),
       ('chiara', 'rizzo', 'chiararizzo@outlook.it', 'rizzo');


SELECT u.idutente, u.nome, u.cognome, u.email, u.password from utente u inner join albumutente au on u.idutente = au.idutente where au.idalbum = 88;

create or replace function funzionealbum() returns trigger as
$$
declare
    nome1 varchar(50);
begin
    nome1 = concat(NEW.nome, ' ', 'album personale');
    insert into album (nome, idowner, privacy)
    values (nome1, NEW.idutente, false);

    return NEW;
END;
$$ language plpgsql;

create trigger t19
    after insert
    on utente
    for each row
execute function funzionealbum();

INSERT INTO ALBUM (nome, idowner, privacy) VALUES
('Vacanze Estive', 83, TRUE),
('Compleanno 2023', 84, FALSE),
('Eventi Aziendali', 85, TRUE),
('Album Famiglia', 86, FALSE),
('Viaggi in Europa', 87, TRUE),
('Foto Casuali', 88, FALSE),
('Foto Artistiche', 89, TRUE),
('Album Animali', 90, FALSE),
('Progetti Personali', 91, TRUE),
('Vacanze Invernali', 92, FALSE),
('Feste Natalizie', 93, TRUE),
('Gite Fuori Porta', 94, FALSE),
('Escursioni Montane', 95, TRUE),
('Ricordi di Infanzia', 96, FALSE),
('Paesaggi Naturali', 97, TRUE),
('Ricordi di Famiglia', 98, FALSE),
('Album Cittadino', 99, TRUE),
('Momenti Speciali', 100, FALSE),
('Viaggi Lavorativi', 101, TRUE),
('Esperienze di Vita', 102, FALSE),
('Cerimonie', 103, TRUE),
('Attività Sportive', 104, FALSE),
('Foto di Studio', 105, TRUE),
('Matrimonio', 106, FALSE),
('Foto Culinarie', 107, TRUE),
('Giornate Estive', 108, FALSE),
('Arte Urbana', 109, TRUE),
('Fotografie di Viaggi', 110, FALSE),
('Escursioni Estive', 111, TRUE),
('Foto Naturalistiche', 112, FALSE),
('Serate con Amici', 113, TRUE),
('Eventi Pubblici', 114, FALSE),
('Weekend di Relax', 115, TRUE);

INSERT INTO albumutente (idalbum, idutente) VALUES
(162, 83),
(164, 90),
(166, 84),
(168, 91),
(170, 85),
(172, 92),
(174, 86),
(176, 93),
(178, 87),
(180, 94),
(182, 88),
(184, 95),
(186, 89),
(188, 96),
(190, 100),
(192, 102),
(162, 104),
(164, 106),
(166, 108),
(168, 110);

INSERT INTO dispostivo(tipologia, idutente) VALUES
('iPhone 11', 83),
('Canon EOS 5D', 84),
('Samsung Galaxy S20', 85),
('iPad Pro', 86),
('Nikon D3500', 87),
('Huawei P30', 88),
('Sony Alpha a7 III', 89),
('Google Pixel 5', 90),
('Canon EOS R', 91),
('iPhone 12', 92),
('Samsung Galaxy Note 10', 93),
('Sony RX100 VII', 94),
('iPad Air', 95),
('Nikon Z6', 96),
('OnePlus 8T', 97),
('Canon PowerShot G7 X', 98),
('Samsung Galaxy S21', 99),
('iPhone SE', 100),
('Sony A6400', 101),
('Huawei Mate 40', 102);


INSERT INTO foto (iddispositivo, idutente) VALUES
(28, 83),
(29, 84),
(30, 85),
(31, 86),
(32, 87),
(33, 88),
(34, 89),
(35, 90),
(36, 91),
(37, 92),
(38, 93),
(39, 94),
(40, 95),
(41, 96),
(42, 97),
(43, 98),
(44, 99),
(45, 100),
(46, 101),
(47, 102),
(28, 103),
(29, 104),
(30, 105),
(31, 106),
(32, 107),
(33, 108),
(34, 109),
(35, 110),
(36, 111),
(37, 112),
(38, 113),
(39, 114),
(40, 115),
(41, 83),
(42, 84),
(43, 85),
(44, 86),
(45, 87),
(46, 88),
(47, 89);

INSERT INTO fotoalbum (idfoto, idalbum) VALUES
(7, 127),
(8, 128),
(9, 129),
(10, 130),
(11, 131),
(12, 132),
(13, 133),
(14, 134),
(15, 135),
(16, 136),
(17, 137),
(18, 138),
(19, 139),
(20, 140),
(21, 141),
(22, 142),
(23, 143),
(24, 144),
(25, 145),
(26, 146),
(27, 147),
(28, 148),
(29, 149),
(30, 150),
(31, 151),
(32, 152),
(33, 153),
(34, 154),
(35, 155),
(36, 156),
(37, 157),
(38, 158),
(39, 159),
(40, 160),
(41, 161),
(42, 162),
(43, 163),
(44, 164),
(45, 165),
(46, 166);


INSERT INTO luogofoto(idfoto, idluogo) VALUES
(7, 1),
(8, 2),
(9, 3),
(10, 4),
(11, 5),
(12, 6),
(13, 7),
(14, 8),
(15, 9),
(16, 10),
(17, 11),
(18, 12),
(19, 13),
(20, 14),
(21, 15),
(22, 16),
(23, 1),
(24, 2),
(25, 3),
(26, 4),
(27, 5),
(28, 6),
(29, 7),
(30, 8),
(31, 9),
(32, 10),
(33, 11),
(34, 12),
(35, 13),
(36, 14),
(37, 15),
(38, 16),
(39, 1),
(40, 2),
(41, 3),
(42, 4),
(43, 5),
(44, 6),
(45, 7),
(46, 8);

INSERT INTO soggetto (categoria) VALUES
('Persona'),
('Animale'),
('Paesaggio'),
('Oggetto'),
('Gruppo di persone'),
('Famiglia'),
('Ritratto'),
('Animali domestici'),
('Natura'),
('Architettura'),
('Cibo'),
('Eventi'),
('Sport'),
('Automobili'),
('Fiori'),
('Arte'),
('Spiaggia'),
('Montagna'),
('Festival'),
('Mario Rossi'),
('Luca Bianchi'),
('Giulia Verdi'),
('Francesca Neri'),
('Alessandro Gallo'),
('Sara Colombo'),
('Davide Conti'),
('Chiara Ferrari'),
('Marco Esposito'),
('Laura Rinaldi'),
('Simone Russo');


INSERT INTO soggettofoto (idsoggetto, idfoto) VALUES
(1, 7),
(2, 8),
(3, 9),
(4, 10),
(5, 11),
(6, 12),
(7, 13),
(8, 14),
(9, 15),
(10, 16),
(11, 17),
(12, 18),
(13, 19),
(14, 20),
(15, 21),
(16, 22),
(17, 23),
(18, 24),
(19, 25),
(20, 26),
(21, 27),
(22, 28),
(23, 29),
(24, 30),
(25, 31),
(26, 32),
(27, 33),
(28, 34),
(29, 35),
(30, 36),
(31, 37),
(32, 38),
(33, 39),
(34, 40),
(35, 41),
(36, 42),
(37, 43),
(38, 44),
(39, 45),
(40, 46),
(41, 7),
(42, 8),
(43, 9),
(44, 10),
(45, 11),
(46, 12),
(47, 13),
(48, 14),
(49, 15),
(50, 16),
(51, 17);

select idfoto from luogofoto where idluogo = 2;

INSERT INTO fotoalbum (idfoto, idalbum) VALUES
(7, 128),
(9, 128),
(10, 128),
(11, 128),
(12, 128),
(13, 128),
(14, 128),
(15, 128),
(16, 128),
(17, 128),
(18, 128),
(19, 128),
(20, 128),
(21, 128),
(22, 128),
(23, 128),
(24, 128),
(25, 128),
(26, 128),
(27, 128),
(28, 128),
(29, 128),
(30, 128),
(31, 128),
(32, 128),
(33, 128),
(34, 128),
(35, 128),
(36, 128),
(37, 128),
(38, 128),
(39, 128),
(40, 128),
(41, 128),
(42, 128),
(43, 128),
(44, 128),
(45, 128),
(46, 128);


