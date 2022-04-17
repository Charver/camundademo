create table loans
(
	id uuid not null,
	comment varchar ,
	approved bool,
	interest_rate varchar ,
	amount varchar ,
	final_amount varchar,
	month_payment varchar ,
	date_finish timestamp
);

create unique index LOANS_ID_UINDEX
	on loans (id);

alter table loans
	add constraint LOANS_PK
		primary key (id);

create table interest
(
	id uuid not null,
	month_amount int,
	interest_rate double
);

create unique index INTEREST_RATE_UINDEX
	on interest (interest_rate);

alter table interest
	add constraint INTEREST_PK
		primary key (id);
