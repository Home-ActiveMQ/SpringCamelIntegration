
DROP TABLE if EXISTS bank

CREATE TABLE if NOT EXISTS bank
(
  id         serial NOT NULL CONSTRAINT bank_pk PRIMARY KEY,
  unique_url varchar(256),
  title      varchar(256)
);

-- INSERT INTO public.bank (id, unique_url, title) VALUES (1, 'test1Processor', 'Test-1');
-- INSERT INTO public.bank (id, unique_url, title) VALUES (2, 'test2Processor', 'Test-2');
-- INSERT INTO public.bank (id, unique_url, title) VALUES (3, 'test3Processor', 'Test-3');

INSERT INTO public.bank (id, unique_url, title) VALUES (1, 'test1', 'Test-1');
INSERT INTO public.bank (id, unique_url, title) VALUES (2, 'test2', 'Test-2');
INSERT INTO public.bank (id, unique_url, title) VALUES (3, 'test3', 'Test-3');