create table locations
(
    location_id int auto_increment primary key,
    name        varchar(50) not null unique
);

create table pictures
(
    picture_id int auto_increment primary key,
    url        varchar(255) not null,
    public_id  varchar(255) not null unique
);

-- requirements & skills tables possibly be the same --

create table requirements
(
    requirement_id int auto_increment primary key,
    type           varchar(50)
);

create table skills
(
    skill_id int auto_increment primary key,
    type     varchar(50)
);


create table companies
(
    company_id  int auto_increment primary key,
    name varchar(50) not null,
    description varchar(500) not null,
    location_id int,
    contacts    varchar(255) not null,
    picture_id  int,

    constraint companies_pictures_picture_id_fk
        foreign key (picture_id) references pictures (picture_id) on delete set null,

    constraint companies_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null

    -- active number of job ads --
    -- successful matches --
);


create table professionals
(
    professional_id int auto_increment primary key,
    summary         varchar(500),
    location_id     int,
    status          varchar(50) not null default 'Active',
    picture_id      int,

    -- active number of job applications --
    -- list of matches --

    constraint professionals_pictures_picture_id_fk
        foreign key (picture_id) references pictures (picture_id) on delete set null,

    constraint professionals_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null
);

create table job_ads
(
    job_ad_id        int auto_increment primary key,
    position_title   varchar(50) not null,
    min_salary_bound double  not null,
    max_salary_bound double  not null,
    job_description  varchar(1000),
    location_id      int,
    status           varchar(50) not null default 'Active',

    -- list of match requests - visible to the Job ad's creator --
    -- if positive match --> ad is Archived & professional set to Busy

    constraint job_ads_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null,

    check (min_salary_bound >= 1),
    check (max_salary_bound >= 1 and max_salary_bound <= 500000)
);

create table job_applications
(
    job_application_id int auto_increment primary key,
    min_desired_salary double  not null,
    max_desired_salary double  not null,
    motivation_letter  varchar(1000),
    location_id        int,
    status             varchar(50) not null default 'Hidden',

    -- list of match requests - visible to the Job application's creator --

    constraint job_applications_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null
);

