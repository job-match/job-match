create table statuses
(
    status_id int auto_increment primary key,
    type      varchar(50) not null unique
);

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
    name        varchar(50)  not null,
    username    varchar(20)  not null unique,
    password    varchar(15)  not null,
    email       varchar(50)  not null unique,
    description varchar(500) not null,
    location_id int,
    contacts    varchar(255) not null,
    picture_id  int,

    constraint companies_pictures_picture_id_fk
        foreign key (picture_id) references pictures (picture_id) on delete set null,

    constraint companies_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null
);


create table professionals
(
    professional_id int auto_increment primary key,
    username        varchar(20) not null unique,
    password        varchar(15) not null,
    first_name      varchar(32) not null,
    last_name       varchar(32) not null,
    email           varchar(50) not null unique,
    summary         varchar(500),
    location_id     int,
    status_id       int default 1,
    picture_id      int,

    constraint professionals_pictures_picture_id_fk
        foreign key (picture_id) references pictures (picture_id) on delete set null,

    constraint professionals_statuses_status_id_fk
        foreign key (status_id) references statuses (status_id) on delete set null,

    constraint professionals_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null
);

create table job_ads
(
    job_ad_id           int auto_increment primary key,
    position_title      varchar(50) not null,
    min_salary_boundary double      not null,
    max_salary_boundary double      not null,
    job_description     varchar(1000),
    location_id         int,
    company_id          int,
    status_id           int default 1,
    created_at timestamp default current_timestamp,

    constraint job_ads_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null,

    constraint job_ads_statuses_status_id_fk
        foreign key (status_id) references statuses (status_id) on delete set null,

    constraint job_ads_companies_company_id_fk
        foreign key (company_id) references companies (company_id) on delete set null,

    check (min_salary_boundary >= 1),
    check (max_salary_boundary >= 1 and max_salary_boundary <= 500000)
);

create table job_applications
(
    job_application_id int auto_increment primary key,
    min_desired_salary double not null,
    max_desired_salary double not null,
    motivation_letter  varchar(1000),
    location_id        int,
    status_id          int default 4,
    professional_id    int,
    created_at timestamp default current_timestamp,

    constraint job_applications_locations_location_id_fk
        foreign key (location_id) references locations (location_id) on delete set null,

    constraint job_applications_statuses_status_id_fk
        foreign key (status_id) references statuses (status_id) on delete set null,

    constraint job_applications_professionals_professional_id_fk
        foreign key (professional_id) references professionals (professional_id) on delete set null,
    check (min_desired_salary >= 1),
    check (min_desired_salary >= 1 and max_desired_salary <= 500000)
);

create table job_ads_requirements
(
    job_ad_id      int,
    requirement_id int,
    primary key (job_ad_id, requirement_id),

    foreign key (job_ad_id) references job_ads (job_ad_id) on delete cascade,
    foreign key (requirement_id) references requirements (requirement_id) on delete cascade
);

create table job_ads_job_applications
(
    job_ad_id          int,
    job_application_id int,
    primary key (job_ad_id, job_application_id),

    foreign key (job_ad_id) references job_ads (job_ad_id) on delete cascade,
    foreign key (job_application_id) references job_applications (job_application_id) on delete cascade
);

create table job_applications_skills
(
    job_application_id int,
    skill_id           int,
    primary key (job_application_id, skill_id),

    foreign key (job_application_id) references job_applications (job_application_id) on delete cascade,
    foreign key (skill_id) references skills (skill_id) on delete cascade
);

create table job_applications_job_ads
(
    job_application_id int,
    job_ad_id          int,
    primary key (job_application_id, job_ad_id),

    foreign key (job_application_id) references job_applications (job_application_id) on delete cascade,
    foreign key (job_ad_id) references job_ads (job_ad_id) on delete cascade
);

create table matches
(
    match_id           int auto_increment primary key,
    job_ad_id          int,
    job_application_id int,

    foreign key (job_ad_id) references job_ads (job_ad_id) on delete cascade,
    foreign key (job_application_id) references job_applications (job_application_id) on delete cascade
);