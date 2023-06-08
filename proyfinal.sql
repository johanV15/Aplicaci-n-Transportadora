PGDMP         '                {         	   proyfinal    14.7    14.7 p    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    25099 	   proyfinal    DATABASE     h   CREATE DATABASE proyfinal WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Colombia.1252';
    DROP DATABASE proyfinal;
                postgres    false            �           1247    25198    dir    TYPE     ;   CREATE TYPE public.dir AS ENUM (
    'CL 90 C SUR 1-45'
);
    DROP TYPE public.dir;
       public          postgres    false            �           1247    25208    est    TYPE     E   CREATE TYPE public.est AS ENUM (
    'En camino',
    'Terminado'
);
    DROP TYPE public.est;
       public          postgres    false            x           1247    25179    gen    DOMAIN     �   CREATE DOMAIN public.gen AS character varying NOT NULL
	CONSTRAINT gen_check CHECK ((((VALUE)::text = 'Femenino'::text) OR ((VALUE)::text = 'Masculino'::text) OR ((VALUE)::text = 'No Binario'::text) OR ((VALUE)::text = 'No Especificado'::text)));
    DROP DOMAIN public.gen;
       public          postgres    false            �           1247    25202    m_pago    TYPE     E   CREATE TYPE public.m_pago AS ENUM (
    'Tarjeta',
    'Efectivo'
);
    DROP TYPE public.m_pago;
       public          postgres    false                       1247    25190    nom_cat    TYPE     T   CREATE TYPE public.nom_cat AS ENUM (
    'Normal',
    'Especial',
    'Urgente'
);
    DROP TYPE public.nom_cat;
       public          postgres    false            |           1247    25182    nom_serv    TYPE     W   CREATE TYPE public.nom_serv AS ENUM (
    'Pasajeros',
    'Alimentos',
    'Todos'
);
    DROP TYPE public.nom_serv;
       public          postgres    false            �           1247    25214 
   valoracion    TYPE     W   CREATE TYPE public.valoracion AS ENUM (
    '1',
    '2',
    '3',
    '4',
    '5'
);
    DROP TYPE public.valoracion;
       public          postgres    false            �            1255    25329    calc_valorserv(real, real)    FUNCTION     q   CREATE FUNCTION public.calc_valorserv(x real, y real) RETURNS real
    LANGUAGE sql
    AS $$
		select x+y;
	$$;
 5   DROP FUNCTION public.calc_valorserv(x real, y real);
       public          postgres    false            �            1255    25331    cant_servicios_mes()    FUNCTION     ]  CREATE FUNCTION public.cant_servicios_mes() RETURNS TABLE(mes character varying, servicios_pasajeros bigint, servicios_alimentos bigint)
    LANGUAGE sql
    AS $$
	select to_char(fecha::date, 'tmmonth') as mes,
		count(case when id_serv=1 then 1 end),
		count(case when id_serv=2 then 1 end)
	from reg_servicio
	group by mes
	order by mes desc
$$;
 +   DROP FUNCTION public.cant_servicios_mes();
       public          postgres    false            �            1255    25334    cond_disponible(integer)    FUNCTION     �  CREATE FUNCTION public.cond_disponible(serv integer) RETURNS bigint
    LANGUAGE sql
    AS $$
    select c.id_cond from conductor c
	inner join vehiculo v using (id_cond)
	where (c.id_cond not in (select id_cond from reg_servicio)
        and (v.id_serv=serv or v.id_serv=3))
	or (c.id_cond not in (select id_cond from reg_servicio
	                      where estado = 'En camino')
	    and (id_serv=serv or id_serv=3))
	order by random() limit 1;
$$;
 4   DROP FUNCTION public.cond_disponible(serv integer);
       public          postgres    false            �            1255    25332    log_servs()    FUNCTION     4  CREATE FUNCTION public.log_servs() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
Begin
	insert into "log_reg_servicio" values
	(old.id_reg, old.id_cond, old.id_cliente, old.id_serv,
	 old.val_servicio, old.medio_pago, old.fecha,
	 old.id_cat, old.id_ruta, old.estado, old.valoracion);
	return new;
End;
$$;
 "   DROP FUNCTION public.log_servs();
       public          postgres    false            �            1255    25330    valor_total_servicios()    FUNCTION     U  CREATE FUNCTION public.valor_total_servicios() RETURNS TABLE(servicio public.nom_serv, categoria public.nom_cat, valor_total real)
    LANGUAGE sql
    AS $$
	select s.nombre, c.nombre, sum(rs.val_servicio)
	from reg_servicio rs
	inner join servicio s using (id_serv)
	inner join categoria c using (id_cat)
	group by s.nombre, c.nombre;
$$;
 .   DROP FUNCTION public.valor_total_servicios();
       public          postgres    false    892    895            �            1259    25123    admin    TABLE     �   CREATE TABLE public.admin (
    id_admin bigint NOT NULL,
    nombre character varying(25) NOT NULL,
    usuario character varying(10) NOT NULL
);
    DROP TABLE public.admin;
       public         heap    postgres    false            �            1259    25141 	   categoria    TABLE     }   CREATE TABLE public.categoria (
    id_cat integer NOT NULL,
    nombre public.nom_cat NOT NULL,
    tarifa real NOT NULL
);
    DROP TABLE public.categoria;
       public         heap    postgres    false    895            �            1259    25140    categoria_id_cat_seq    SEQUENCE     �   CREATE SEQUENCE public.categoria_id_cat_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.categoria_id_cat_seq;
       public          postgres    false    220            �           0    0    categoria_id_cat_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.categoria_id_cat_seq OWNED BY public.categoria.id_cat;
          public          postgres    false    219            �            1259    25115    cliente    TABLE     ^  CREATE TABLE public.cliente (
    id_cliente bigint NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL,
    direccion character varying(25) NOT NULL,
    genero public.gen DEFAULT 'No Especificado'::character varying,
    nacionalidad character varying(10),
    usuario character varying(10) NOT NULL
);
    DROP TABLE public.cliente;
       public         heap    postgres    false    888    888            �            1259    25100 	   conductor    TABLE     b  CREATE TABLE public.conductor (
    id_cond bigint NOT NULL,
    nombre character varying(25) NOT NULL,
    apellido character varying(25) NOT NULL,
    direccion character varying(25),
    genero public.gen DEFAULT 'No Especificado'::character varying,
    nacionalidad character varying(10),
    foto oid,
    usuario character varying(10) NOT NULL
);
    DROP TABLE public.conductor;
       public         heap    postgres    false    888    888            �            1259    25171    log_reg_servicio    TABLE     o  CREATE TABLE public.log_reg_servicio (
    id_reg integer NOT NULL,
    id_cond bigint,
    id_cliente bigint,
    id_serv integer NOT NULL,
    val_servicio real,
    medio_pago character varying(30),
    fecha character varying(10),
    id_cat integer NOT NULL,
    id_ruta integer NOT NULL,
    estado character varying(15),
    valoracion character varying(1)
);
 $   DROP TABLE public.log_reg_servicio;
       public         heap    postgres    false            �            1259    25169    log_reg_servicio_id_cat_seq    SEQUENCE     �   CREATE SEQUENCE public.log_reg_servicio_id_cat_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.log_reg_servicio_id_cat_seq;
       public          postgres    false    232            �           0    0    log_reg_servicio_id_cat_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.log_reg_servicio_id_cat_seq OWNED BY public.log_reg_servicio.id_cat;
          public          postgres    false    230            �            1259    25167    log_reg_servicio_id_reg_seq    SEQUENCE     �   CREATE SEQUENCE public.log_reg_servicio_id_reg_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.log_reg_servicio_id_reg_seq;
       public          postgres    false    232            �           0    0    log_reg_servicio_id_reg_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.log_reg_servicio_id_reg_seq OWNED BY public.log_reg_servicio.id_reg;
          public          postgres    false    228            �            1259    25170    log_reg_servicio_id_ruta_seq    SEQUENCE     �   CREATE SEQUENCE public.log_reg_servicio_id_ruta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.log_reg_servicio_id_ruta_seq;
       public          postgres    false    232            �           0    0    log_reg_servicio_id_ruta_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.log_reg_servicio_id_ruta_seq OWNED BY public.log_reg_servicio.id_ruta;
          public          postgres    false    231            �            1259    25168    log_reg_servicio_id_serv_seq    SEQUENCE     �   CREATE SEQUENCE public.log_reg_servicio_id_serv_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.log_reg_servicio_id_serv_seq;
       public          postgres    false    232            �           0    0    log_reg_servicio_id_serv_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.log_reg_servicio_id_serv_seq OWNED BY public.log_reg_servicio.id_serv;
          public          postgres    false    229            �            1259    25158    reg_servicio    TABLE     �  CREATE TABLE public.reg_servicio (
    id_reg integer NOT NULL,
    id_cond bigint NOT NULL,
    id_cliente bigint NOT NULL,
    id_serv integer NOT NULL,
    val_servicio real NOT NULL,
    medio_pago public.m_pago NOT NULL,
    fecha character varying(10) NOT NULL,
    id_cat integer NOT NULL,
    id_ruta integer NOT NULL,
    estado public.est,
    valoracion public.valoracion
);
     DROP TABLE public.reg_servicio;
       public         heap    postgres    false    907    904    901            �            1259    25156    reg_servicio_id_cat_seq    SEQUENCE     �   CREATE SEQUENCE public.reg_servicio_id_cat_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.reg_servicio_id_cat_seq;
       public          postgres    false    227            �           0    0    reg_servicio_id_cat_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.reg_servicio_id_cat_seq OWNED BY public.reg_servicio.id_cat;
          public          postgres    false    225            �            1259    25154    reg_servicio_id_reg_seq    SEQUENCE     �   CREATE SEQUENCE public.reg_servicio_id_reg_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.reg_servicio_id_reg_seq;
       public          postgres    false    227            �           0    0    reg_servicio_id_reg_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.reg_servicio_id_reg_seq OWNED BY public.reg_servicio.id_reg;
          public          postgres    false    223            �            1259    25157    reg_servicio_id_ruta_seq    SEQUENCE     �   CREATE SEQUENCE public.reg_servicio_id_ruta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.reg_servicio_id_ruta_seq;
       public          postgres    false    227            �           0    0    reg_servicio_id_ruta_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.reg_servicio_id_ruta_seq OWNED BY public.reg_servicio.id_ruta;
          public          postgres    false    226            �            1259    25155    reg_servicio_id_serv_seq    SEQUENCE     �   CREATE SEQUENCE public.reg_servicio_id_serv_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.reg_servicio_id_serv_seq;
       public          postgres    false    227            �           0    0    reg_servicio_id_serv_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.reg_servicio_id_serv_seq OWNED BY public.reg_servicio.id_serv;
          public          postgres    false    224            �            1259    25148    ruta    TABLE     �   CREATE TABLE public.ruta (
    id_ruta integer NOT NULL,
    dir_origen public.dir DEFAULT 'CL 90 C SUR 1-45'::public.dir NOT NULL,
    dir_destino character varying(25) NOT NULL
);
    DROP TABLE public.ruta;
       public         heap    postgres    false    898    898            �            1259    25147    ruta_id_ruta_seq    SEQUENCE     �   CREATE SEQUENCE public.ruta_id_ruta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.ruta_id_ruta_seq;
       public          postgres    false    222            �           0    0    ruta_id_ruta_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.ruta_id_ruta_seq OWNED BY public.ruta.id_ruta;
          public          postgres    false    221            �            1259    25134    servicio    TABLE     u   CREATE TABLE public.servicio (
    id_serv integer NOT NULL,
    nombre public.nom_serv NOT NULL,
    tarifa real
);
    DROP TABLE public.servicio;
       public         heap    postgres    false    892            �            1259    25133    servicio_id_serv_seq    SEQUENCE     �   CREATE SEQUENCE public.servicio_id_serv_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.servicio_id_serv_seq;
       public          postgres    false    218            �           0    0    servicio_id_serv_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.servicio_id_serv_seq OWNED BY public.servicio.id_serv;
          public          postgres    false    217            �            1259    25319    servs_cliente_vw    VIEW     �  CREATE VIEW public.servs_cliente_vw AS
 SELECT rs.id_reg AS nroregistro,
    cl.id_cliente AS identificacion,
    cl.nombre AS nombre_cliente,
    s.nombre AS servicio,
    c.nombre AS categoria,
    rs.val_servicio AS valor_servicio,
    rs.fecha,
    rs.estado
   FROM (((public.reg_servicio rs
     JOIN public.cliente cl USING (id_cliente))
     JOIN public.servicio s USING (id_serv))
     JOIN public.categoria c USING (id_cat));
 #   DROP VIEW public.servs_cliente_vw;
       public          postgres    false    227    227    227    227    227    227    227    220    220    218    218    213    213    892    895    904            �            1259    25324    servs_cond_vw    VIEW       CREATE VIEW public.servs_cond_vw AS
 SELECT rs.id_reg AS nroregistro,
    cl.id_cond AS identificacion,
    cl.nombre AS nombre_conductor,
    s.nombre AS servicio,
    c.nombre AS categoria,
    rs.val_servicio AS valor_servicio,
    r.dir_origen AS origen,
    r.dir_destino AS destino,
    rs.fecha,
    rs.estado
   FROM ((((public.reg_servicio rs
     JOIN public.conductor cl USING (id_cond))
     JOIN public.servicio s USING (id_serv))
     JOIN public.categoria c USING (id_cat))
     JOIN public.ruta r USING (id_ruta));
     DROP VIEW public.servs_cond_vw;
       public          postgres    false    227    209    209    227    218    218    227    227    220    227    220    222    227    222    227    222    227    898    895    892    904            �            1259    25120    tel_cliente    TABLE     b   CREATE TABLE public.tel_cliente (
    id_cliente bigint NOT NULL,
    telefono bigint NOT NULL
);
    DROP TABLE public.tel_cliente;
       public         heap    postgres    false            �            1259    25105    tel_conductor    TABLE     a   CREATE TABLE public.tel_conductor (
    id_cond bigint NOT NULL,
    telefono bigint NOT NULL
);
 !   DROP TABLE public.tel_conductor;
       public         heap    postgres    false            �            1259    25128    usuario    TABLE     u   CREATE TABLE public.usuario (
    usuario character varying(10) NOT NULL,
    pass character varying(25) NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    25109    vehiculo    TABLE     �   CREATE TABLE public.vehiculo (
    placa character varying(10) NOT NULL,
    modelo integer NOT NULL,
    id_cond bigint NOT NULL,
    id_serv integer NOT NULL
);
    DROP TABLE public.vehiculo;
       public         heap    postgres    false            �            1259    25108    vehiculo_id_serv_seq    SEQUENCE     �   CREATE SEQUENCE public.vehiculo_id_serv_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.vehiculo_id_serv_seq;
       public          postgres    false    212            �           0    0    vehiculo_id_serv_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.vehiculo_id_serv_seq OWNED BY public.vehiculo.id_serv;
          public          postgres    false    211            �           2604    25144    categoria id_cat    DEFAULT     t   ALTER TABLE ONLY public.categoria ALTER COLUMN id_cat SET DEFAULT nextval('public.categoria_id_cat_seq'::regclass);
 ?   ALTER TABLE public.categoria ALTER COLUMN id_cat DROP DEFAULT;
       public          postgres    false    219    220    220            �           2604    25174    log_reg_servicio id_reg    DEFAULT     �   ALTER TABLE ONLY public.log_reg_servicio ALTER COLUMN id_reg SET DEFAULT nextval('public.log_reg_servicio_id_reg_seq'::regclass);
 F   ALTER TABLE public.log_reg_servicio ALTER COLUMN id_reg DROP DEFAULT;
       public          postgres    false    232    228    232            �           2604    25175    log_reg_servicio id_serv    DEFAULT     �   ALTER TABLE ONLY public.log_reg_servicio ALTER COLUMN id_serv SET DEFAULT nextval('public.log_reg_servicio_id_serv_seq'::regclass);
 G   ALTER TABLE public.log_reg_servicio ALTER COLUMN id_serv DROP DEFAULT;
       public          postgres    false    232    229    232            �           2604    25176    log_reg_servicio id_cat    DEFAULT     �   ALTER TABLE ONLY public.log_reg_servicio ALTER COLUMN id_cat SET DEFAULT nextval('public.log_reg_servicio_id_cat_seq'::regclass);
 F   ALTER TABLE public.log_reg_servicio ALTER COLUMN id_cat DROP DEFAULT;
       public          postgres    false    230    232    232            �           2604    25177    log_reg_servicio id_ruta    DEFAULT     �   ALTER TABLE ONLY public.log_reg_servicio ALTER COLUMN id_ruta SET DEFAULT nextval('public.log_reg_servicio_id_ruta_seq'::regclass);
 G   ALTER TABLE public.log_reg_servicio ALTER COLUMN id_ruta DROP DEFAULT;
       public          postgres    false    231    232    232            �           2604    25161    reg_servicio id_reg    DEFAULT     z   ALTER TABLE ONLY public.reg_servicio ALTER COLUMN id_reg SET DEFAULT nextval('public.reg_servicio_id_reg_seq'::regclass);
 B   ALTER TABLE public.reg_servicio ALTER COLUMN id_reg DROP DEFAULT;
       public          postgres    false    223    227    227            �           2604    25162    reg_servicio id_serv    DEFAULT     |   ALTER TABLE ONLY public.reg_servicio ALTER COLUMN id_serv SET DEFAULT nextval('public.reg_servicio_id_serv_seq'::regclass);
 C   ALTER TABLE public.reg_servicio ALTER COLUMN id_serv DROP DEFAULT;
       public          postgres    false    227    224    227            �           2604    25163    reg_servicio id_cat    DEFAULT     z   ALTER TABLE ONLY public.reg_servicio ALTER COLUMN id_cat SET DEFAULT nextval('public.reg_servicio_id_cat_seq'::regclass);
 B   ALTER TABLE public.reg_servicio ALTER COLUMN id_cat DROP DEFAULT;
       public          postgres    false    225    227    227            �           2604    25164    reg_servicio id_ruta    DEFAULT     |   ALTER TABLE ONLY public.reg_servicio ALTER COLUMN id_ruta SET DEFAULT nextval('public.reg_servicio_id_ruta_seq'::regclass);
 C   ALTER TABLE public.reg_servicio ALTER COLUMN id_ruta DROP DEFAULT;
       public          postgres    false    226    227    227            �           2604    25151    ruta id_ruta    DEFAULT     l   ALTER TABLE ONLY public.ruta ALTER COLUMN id_ruta SET DEFAULT nextval('public.ruta_id_ruta_seq'::regclass);
 ;   ALTER TABLE public.ruta ALTER COLUMN id_ruta DROP DEFAULT;
       public          postgres    false    221    222    222            �           2604    25137    servicio id_serv    DEFAULT     t   ALTER TABLE ONLY public.servicio ALTER COLUMN id_serv SET DEFAULT nextval('public.servicio_id_serv_seq'::regclass);
 ?   ALTER TABLE public.servicio ALTER COLUMN id_serv DROP DEFAULT;
       public          postgres    false    218    217    218            �           2604    25112    vehiculo id_serv    DEFAULT     t   ALTER TABLE ONLY public.vehiculo ALTER COLUMN id_serv SET DEFAULT nextval('public.vehiculo_id_serv_seq'::regclass);
 ?   ALTER TABLE public.vehiculo ALTER COLUMN id_serv DROP DEFAULT;
       public          postgres    false    212    211    212            y          0    25123    admin 
   TABLE DATA           :   COPY public.admin (id_admin, nombre, usuario) FROM stdin;
    public          postgres    false    215   ��       ~          0    25141 	   categoria 
   TABLE DATA           ;   COPY public.categoria (id_cat, nombre, tarifa) FROM stdin;
    public          postgres    false    220   ��       w          0    25115    cliente 
   TABLE DATA           i   COPY public.cliente (id_cliente, nombre, apellido, direccion, genero, nacionalidad, usuario) FROM stdin;
    public          postgres    false    213   ��       s          0    25100 	   conductor 
   TABLE DATA           n   COPY public.conductor (id_cond, nombre, apellido, direccion, genero, nacionalidad, foto, usuario) FROM stdin;
    public          postgres    false    209   v�       �          0    25171    log_reg_servicio 
   TABLE DATA           �   COPY public.log_reg_servicio (id_reg, id_cond, id_cliente, id_serv, val_servicio, medio_pago, fecha, id_cat, id_ruta, estado, valoracion) FROM stdin;
    public          postgres    false    232   Ë       �          0    25158    reg_servicio 
   TABLE DATA           �   COPY public.reg_servicio (id_reg, id_cond, id_cliente, id_serv, val_servicio, medio_pago, fecha, id_cat, id_ruta, estado, valoracion) FROM stdin;
    public          postgres    false    227   ��       �          0    25148    ruta 
   TABLE DATA           @   COPY public.ruta (id_ruta, dir_origen, dir_destino) FROM stdin;
    public          postgres    false    222   5�       |          0    25134    servicio 
   TABLE DATA           ;   COPY public.servicio (id_serv, nombre, tarifa) FROM stdin;
    public          postgres    false    218   l�       x          0    25120    tel_cliente 
   TABLE DATA           ;   COPY public.tel_cliente (id_cliente, telefono) FROM stdin;
    public          postgres    false    214   ��       t          0    25105    tel_conductor 
   TABLE DATA           :   COPY public.tel_conductor (id_cond, telefono) FROM stdin;
    public          postgres    false    210   �       z          0    25128    usuario 
   TABLE DATA           0   COPY public.usuario (usuario, pass) FROM stdin;
    public          postgres    false    216   ��       v          0    25109    vehiculo 
   TABLE DATA           C   COPY public.vehiculo (placa, modelo, id_cond, id_serv) FROM stdin;
    public          postgres    false    212   A�       �           0    0    categoria_id_cat_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.categoria_id_cat_seq', 3, true);
          public          postgres    false    219            �           0    0    log_reg_servicio_id_cat_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.log_reg_servicio_id_cat_seq', 1, false);
          public          postgres    false    230            �           0    0    log_reg_servicio_id_reg_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.log_reg_servicio_id_reg_seq', 1, false);
          public          postgres    false    228            �           0    0    log_reg_servicio_id_ruta_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.log_reg_servicio_id_ruta_seq', 1, false);
          public          postgres    false    231            �           0    0    log_reg_servicio_id_serv_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.log_reg_servicio_id_serv_seq', 1, false);
          public          postgres    false    229            �           0    0    reg_servicio_id_cat_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.reg_servicio_id_cat_seq', 1, false);
          public          postgres    false    225            �           0    0    reg_servicio_id_reg_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.reg_servicio_id_reg_seq', 1, false);
          public          postgres    false    223            �           0    0    reg_servicio_id_ruta_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.reg_servicio_id_ruta_seq', 1, false);
          public          postgres    false    226            �           0    0    reg_servicio_id_serv_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.reg_servicio_id_serv_seq', 1, false);
          public          postgres    false    224            �           0    0    ruta_id_ruta_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.ruta_id_ruta_seq', 1, true);
          public          postgres    false    221            �           0    0    servicio_id_serv_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.servicio_id_serv_seq', 3, true);
          public          postgres    false    217            �           0    0    vehiculo_id_serv_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.vehiculo_id_serv_seq', 1, false);
          public          postgres    false    211            �           2606    25127    admin admin_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (id_admin);
 :   ALTER TABLE ONLY public.admin DROP CONSTRAINT admin_pkey;
       public            postgres    false    215            �           2606    25146    categoria categoria_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id_cat);
 B   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_pkey;
       public            postgres    false    220            �           2606    25119    cliente cliente_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);
 >   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_pkey;
       public            postgres    false    213            �           2606    25104    conductor conductor_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.conductor
    ADD CONSTRAINT conductor_pkey PRIMARY KEY (id_cond);
 B   ALTER TABLE ONLY public.conductor DROP CONSTRAINT conductor_pkey;
       public            postgres    false    209            �           2606    25166    reg_servicio reg_servicio_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.reg_servicio
    ADD CONSTRAINT reg_servicio_pkey PRIMARY KEY (id_reg);
 H   ALTER TABLE ONLY public.reg_servicio DROP CONSTRAINT reg_servicio_pkey;
       public            postgres    false    227            �           2606    25153    ruta ruta_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.ruta
    ADD CONSTRAINT ruta_pkey PRIMARY KEY (id_ruta);
 8   ALTER TABLE ONLY public.ruta DROP CONSTRAINT ruta_pkey;
       public            postgres    false    222            �           2606    25139    servicio servicio_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (id_serv);
 @   ALTER TABLE ONLY public.servicio DROP CONSTRAINT servicio_pkey;
       public            postgres    false    218            �           2606    25271    tel_cliente tlfcl_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public.tel_cliente
    ADD CONSTRAINT tlfcl_pk PRIMARY KEY (id_cliente, telefono);
 >   ALTER TABLE ONLY public.tel_cliente DROP CONSTRAINT tlfcl_pk;
       public            postgres    false    214    214            �           2606    25240    tel_conductor tlfcond_pk 
   CONSTRAINT     e   ALTER TABLE ONLY public.tel_conductor
    ADD CONSTRAINT tlfcond_pk PRIMARY KEY (id_cond, telefono);
 B   ALTER TABLE ONLY public.tel_conductor DROP CONSTRAINT tlfcond_pk;
       public            postgres    false    210    210            �           2606    25132    usuario usuario_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    216            �           2606    25114    vehiculo vehiculo_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.vehiculo
    ADD CONSTRAINT vehiculo_pkey PRIMARY KEY (placa);
 @   ALTER TABLE ONLY public.vehiculo DROP CONSTRAINT vehiculo_pkey;
       public            postgres    false    212            �           2620    25333    reg_servicio log_servs_tr    TRIGGER     r   CREATE TRIGGER log_servs_tr AFTER DELETE ON public.reg_servicio FOR EACH ROW EXECUTE FUNCTION public.log_servs();
 2   DROP TRIGGER log_servs_tr ON public.reg_servicio;
       public          postgres    false    238    227            �           2606    25277    admin admuser_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admuser_fk FOREIGN KEY (usuario) REFERENCES public.usuario(usuario) ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.admin DROP CONSTRAINT admuser_fk;
       public          postgres    false    216    215    3282            �           2606    25256    cliente cluser_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cluser_fk FOREIGN KEY (usuario) REFERENCES public.usuario(usuario) ON DELETE CASCADE;
 ;   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cluser_fk;
       public          postgres    false    216    3282    213            �           2606    25225    conductor conduser_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.conductor
    ADD CONSTRAINT conduser_fk FOREIGN KEY (usuario) REFERENCES public.usuario(usuario) ON DELETE CASCADE;
 ?   ALTER TABLE ONLY public.conductor DROP CONSTRAINT conduser_fk;
       public          postgres    false    3282    216    209            �           2606    25300    reg_servicio rgcl_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.reg_servicio
    ADD CONSTRAINT rgcl_fk FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.reg_servicio DROP CONSTRAINT rgcl_fk;
       public          postgres    false    3276    227    213            �           2606    25295    reg_servicio rgcond_fk    FK CONSTRAINT     ~   ALTER TABLE ONLY public.reg_servicio
    ADD CONSTRAINT rgcond_fk FOREIGN KEY (id_cond) REFERENCES public.conductor(id_cond);
 @   ALTER TABLE ONLY public.reg_servicio DROP CONSTRAINT rgcond_fk;
       public          postgres    false    3270    227    209            �           2606    25305    reg_servicio rgserv_fk    FK CONSTRAINT     }   ALTER TABLE ONLY public.reg_servicio
    ADD CONSTRAINT rgserv_fk FOREIGN KEY (id_serv) REFERENCES public.servicio(id_serv);
 @   ALTER TABLE ONLY public.reg_servicio DROP CONSTRAINT rgserv_fk;
       public          postgres    false    3284    218    227            �           2606    25272    tel_cliente tlfcl_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tel_cliente
    ADD CONSTRAINT tlfcl_fk FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.tel_cliente DROP CONSTRAINT tlfcl_fk;
       public          postgres    false    213    214    3276            �           2606    25241    tel_conductor tlfcond_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tel_conductor
    ADD CONSTRAINT tlfcond_fk FOREIGN KEY (id_cond) REFERENCES public.conductor(id_cond) ON DELETE CASCADE;
 B   ALTER TABLE ONLY public.tel_conductor DROP CONSTRAINT tlfcond_fk;
       public          postgres    false    210    209    3270            �           2606    25246    vehiculo vidcond_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.vehiculo
    ADD CONSTRAINT vidcond_fk FOREIGN KEY (id_cond) REFERENCES public.conductor(id_cond) ON DELETE CASCADE;
 =   ALTER TABLE ONLY public.vehiculo DROP CONSTRAINT vidcond_fk;
       public          postgres    false    3270    212    209            �           2606    25251    vehiculo vidserv_fk    FK CONSTRAINT     z   ALTER TABLE ONLY public.vehiculo
    ADD CONSTRAINT vidserv_fk FOREIGN KEY (id_serv) REFERENCES public.servicio(id_serv);
 =   ALTER TABLE ONLY public.vehiculo DROP CONSTRAINT vidserv_fk;
       public          postgres    false    3284    212    218            y      x�3��*��L��LL�5����� 6%�      ~   4   x�3���/�M��4400�2�t-.HM��MA|c�Т�Լ�TNC0?F��� [�x      w   m   x�34261�,(*MMJ�L,NIK�L��Q�4PH��M,N.�����t����M�L�r�rz�g$�q��敔%srR�,�K��R��2�@�R��b���� Z|&�      s   =   x�34�L��K)M.�/�L,NIK�L�142��M,N.��������&e&r��r��qqq �      �      x������ � �      �   E   x�3�44�44261�4�42500�tMKM.�,��4202�50�52�4�4�t�SHN����������� ��A      �   '   x�3�t�Q�4PpVR0�51�L���$s��qqq vT�      |   4   x�3�H,N�J-�/�44 .#Nǜ��Լ��)XȘ3$?�5������ �ug      x   "   x�341426�4�F�&F�\�H��\1z\\\ ~=M      t      x������ � �      z   2   x�KL�5�L4�J�1�LR�y)@F�!WV~Fb^j1������W� �s      v      x�K,N142�42 ����\1z\\\ 7��     