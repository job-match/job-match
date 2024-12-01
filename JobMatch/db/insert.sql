-- statuses Table --

insert into job_match.statuses (status_id, type)
values (1, 'Active'),
       (2, 'Busy'),
       (3, 'Archived'),
       (4, 'Hidden'),
       (5, 'Private'),
       (6, 'Matched');

-- locations Table --

insert into job_match.locations (location_id, name)
values (1, 'remote'),
       (2, 'hybrid'),
       (3, 'Sofia'),
       (4, 'Plovdiv'),
       (5, 'Varna'),
       (6, 'Burgas');

-- requirements Table --
insert into job_match.requirements (requirement_id, type)
values (1, 'Java'),
       (2, 'Python'),
       (3, 'Project Management'),
       (4, 'Communication Skills'),
       (5, 'Problem-Solving'),
       (6, 'SQL'),
       (7, 'Attention to Detail'),
       (8, 'Leadership'),
       (9, 'Teamwork'),
       (10, 'Time Management'),
       (11, 'Adaptability'),
       (12, 'Analytical Thinking'),
       (13, 'Creativity'),
       (14, 'Client Management'),
       (15, 'Data Analysis'),
       (16, 'Public Speaking'),
       (17, 'Sales Experience'),
       (18, 'HTML'),
       (19, 'Conflict Resolution'),
       (20, 'Negotiation Skills'),
       (21, 'Financial Literacy'),
       (22, 'Product Design'),
       (23, 'Customer Service'),
       (24, 'Strategic Planning'),
       (25, 'Presentation Skills'),
       (26, 'Agile Methodologies'),
       (27, 'Marketing'),
       (28, 'Budget Management'),
       (29, 'Critical Thinking'),
       (30, 'Emotional Intelligence');

-- skills Table --
insert into job_match.skills (skill_id, type)
values (1, 'Java'),
       (2, 'Python'),
       (3, 'Project Management'),
       (4, 'Communication Skills'),
       (5, 'Problem-Solving'),
       (6, 'SQL'),
       (7, 'Attention to Detail'),
       (8, 'Leadership'),
       (9, 'Teamwork'),
       (10, 'Time Management'),
       (11, 'Adaptability'),
       (12, 'Analytical Thinking'),
       (13, 'Creativity'),
       (14, 'Client Management'),
       (15, 'Data Analysis'),
       (16, 'Public Speaking'),
       (17, 'Sales Experience'),
       (18, 'HTML'),
       (19, 'Conflict Resolution'),
       (20, 'Negotiation Skills'),
       (21, 'Financial Literacy'),
       (22, 'Product Design'),
       (23, 'Customer Service'),
       (24, 'Strategic Planning'),
       (25, 'Presentation Skills'),
       (26, 'Agile Methodologies'),
       (27, 'Marketing'),
       (28, 'Budget Management'),
       (29, 'Critical Thinking'),
       (30, 'Emotional Intelligence'),
       -- New skills not in the requirements table
       (31, 'Networking'),
       (32, 'Self-Motivation'),
       (33, 'Computer Literacy'),
       (34, 'Decision Making'),
       (35, 'Multitasking'),
       (36, 'Organization Skills'),
       (37, 'Coding Best Practices'),
       (38, 'Machine Learning'),
       (39, 'Statistical Analysis'),
       (40, 'Customer Relationship Management (CRM)'),
       (41, 'UX/UI Design'),
       (42, 'Research Skills'),
       (43, 'Cloud Computing'),
       (44, 'Data Visualization'),
       (45, 'Software Testing'),
       (46, 'Social Media Management'),
       (47, 'Event Planning'),
       (48, 'Technical Writing'),
       (49, 'Search Engine Optimization (SEO)'),
       (50, 'Content Creation'),
       (51, 'Risk Management'),
       (52, 'Supply Chain Management'),
       (53, 'Quality Assurance'),
       (54, 'Interpersonal Skills'),
       (55, 'Process Improvement');

-- companies Table --
insert into job_match.companies (company_id, name, username, password, email, description, location_id, contacts)
values (1, 'Dev Experts', 'devexperts', 'password', 'devexperts@mail.bg',
        'Dev Experts is a renowned software development company specializing in high-performance trading platforms and financial technology solutions. They provide reliable, scalable, and secure applications tailored to the specific needs of financial institutions globally.',
        3, '123 Technology Ave, Sofia, +1 234 567 8901'),

       (2, 'Green Solutions', 'greensolutions', 'password', 'greensolutions@mail.bg',
        'Green Solutions is an environmental consulting firm dedicated to helping companies minimize their environmental impact. They offer a range of services, including sustainability assessments, waste management, and environmental compliance. Green Solutions believes in empowering businesses to become eco-friendly while maintaining profitability.',
        4, '456 Greenway Blvd, Plovdiv, +1 234 567 8902'),

       (3, 'Tech Innovators', 'techinovators', 'password', 'techinovators@mail.bg',
        'Tech Innovators is a technology consulting firm focused on digital transformation and innovation for businesses of all sizes. Their team specializes in cloud computing, cybersecurity, and big data analytics, assisting clients in modernizing their infrastructure and enhancing security.',
        5, '789 Innovation Dr, Varna, +1 234 567 8903'),

       (4, 'Market Gurus', 'marketgurus', 'password', 'marketgurus@mail.bg',
        'Market Gurus is a leading digital marketing agency that helps companies build strong online presences. With expertise in SEO, content marketing, and social media strategies, they enable clients to reach their target audiences effectively. Market Gurus prides itself on data-driven strategies that yield measurable results.',
        6, '101 Digital St, Burgas, +1 234 567 8904'),

       (5, 'NextGen Health', 'nextgenhealth', 'password', 'k.kirilov@creasevisuals.com',
        'NextGen Health is a healthcare technology company focused on improving patient care through innovative solutions. Their products include electronic health record (EHR) systems, telemedicine platforms, and patient management tools. NextGen Health’s mission is to make healthcare more accessible and efficient by leveraging technology.',
        3, '102 Health Ln, Sofia, +1 234 567 8905'),

       (6, 'Eco Builders', 'ecobuilders', 'password', 'ecobuilders@mail.bg',
        'Eco Builders is a sustainable construction company dedicated to creating environmentally friendly buildings and structures. They focus on using eco-friendly materials, energy-efficient designs, and renewable energy solutions. Eco Builders aims to reduce the carbon footprint of construction projects while providing beautiful, durable buildings.',
        4, '202 Green St, Plovdiv, +1 234 567 8906'),

       (7, 'Edu Future', 'edufutures', 'password', 'edufutures@mail.bg',
        'Edu Future is an edtech company that provides online learning platforms and educational tools for students and professionals. They offer courses in various fields, from programming to business management, all designed to be accessible and engaging. Edu Future aims to revolutionize education by making high-quality learning resources available to everyone.',
        5, '303 Learning Ave, Varna, +1 234 567 8907'),

       (8, 'Agro Corp', 'argrocrop', 'password', 'argrocrop@mail.bg',
        'Agro Corp is an agritech company focused on developing advanced solutions for the agriculture sector. Their technologies include precision farming tools, crop monitoring systems, and sustainable agriculture practices. Agro Corp’s mission is to help farmers optimize production while minimizing environmental impact.',
        6, '404 Harvest Rd, Burgas, +1 234 567 8908'),

       (9, 'Retail Boost', 'retailboost', 'password', 'retailboost@mail.bg',
        'Retail Boost is a retail consulting company that helps businesses improve their customer experience and operational efficiency. They offer solutions for inventory management, point-of-sale systems, and customer engagement strategies. Retail Boost combines industry expertise with technology to deliver insights that drive growth and enhance profitability. ',
        3, '505 Commerce Blvd, Sofia, +1 234 567 8909'),

       (10, 'Auto Nexus', 'autonexus', 'password', 'autonexus@mail.bg',
        'Auto Nexus is an automotive solutions provider specializing in dealership management software and customer service automation. Their products streamline operations for automotive businesses, improving efficiency and customer satisfaction. Auto Nexus’s tools include CRM systems, inventory tracking, and sales management software, tailored specifically for the automotive industry. ',
        4, '606 Motorway Dr, Plovdiv, +1 234 567 8910');

-- professionals Table --
insert into job_match.professionals (professional_id, username, password, first_name, last_name, email, summary,
                                     location_id)
values (1, 'kirilkirilov', 'password', 'Kiril', 'Kirilov', 'kirilovmail90@gmail.com',
        'Experienced software engineer with over 10 years of expertise in backend development and system architecture. Skilled in Java, Python, and cloud-based technologies, specializing in creating scalable and efficient solutions for complex business challenges. Known for analytical thinking, strong problem-solving skills, and the ability to communicate technical information effectively to both technical and non-technical stakeholders.',
        3),

       (2, 'johnsmith', 'password', 'John', 'Smith', 'johnsmith@mail.bg',
        'Digital marketing specialist with a proven track record of designing and implementing successful campaigns across various digital platforms. Proficient in SEO, content marketing, and social media strategies, with a strong understanding of data analytics to drive results. Recognized for creative problem-solving skills and a passion for staying updated with the latest trends in digital marketing.',
        4),

       (3, 'mihailboychev', 'password', 'Mihail', 'Boychev', 'mihailboychev@mail.bg',
        'Project manager with extensive experience in agile methodologies and team coordination within the tech industry. Demonstrates an ability to manage multiple projects, meet deadlines, and ensure high-quality deliverables. Skilled in risk management, strategic planning, and fostering collaboration among diverse teams. Recognized for strong leadership qualities and an organized approach to project execution.',
        5),

       (4, 'todorpetrov', 'password', 'Todor', 'Petrov', 'todorpetrov@mail.bg',
        'Full-stack developer proficient in JavaScript frameworks such as React and Node.js, with a keen interest in building user-friendly applications. Known for strong technical expertise combined with an understanding of UX/UI principles, making it possible to bridge the gap between backend functionality and front-end usability.',
        6),

       (5, 'emilyjones', 'password', 'Emily', 'Jones', 'emilyjones@mail.bg',
        'HR professional with a specialization in talent acquisition and employee relations, bringing over 8 years of experience in recruiting and retention strategies. Known for excellent communication and interpersonal skills, allowing for effective collaboration with hiring managers and candidates. Experienced in conducting interviews, assessing talent, and aligning hiring practices with company goals.',
        3),

       (6, 'peterivanov', 'password', 'Peter', 'Ivanov', 'peterivanov@mail.bg',
        'Data analyst with extensive experience in leveraging statistical methods and data visualization tools to support business decision-making. Proficient in SQL, Python, and Tableau, and skilled at translating data into actionable insights. Known for a methodical approach to data interpretation and a passion for optimizing business performance through data-driven strategies.',
        4),

       (7, 'annabrown', 'password', 'Anna', 'Brown', 'annabrown@mail.bg',
        'Customer service representative with a strong background in communication and conflict resolution. Known for a friendly demeanor, active listening skills, and the ability to handle high-pressure situations calmly and effectively. Experienced in various customer support platforms and CRM tools.',
        5),

       (8, 'georgedimitrov', 'password', 'George', 'Dimitrov', 'georgedimitrov@mail.bg',
        'Graphic designer with a unique blend of creativity and technical skill in visual design. Expert in Adobe Creative Suite and familiar with digital marketing and branding strategies. Known for a strong eye for detail and an ability to create visually compelling content.',
        6),

       (9, 'annavalcheva', 'password', 'Anna', 'Valcheva', 'annaluncheva@mail.bg',
        'Software engineer with a focus on front-end development and a strong foundation in HTML, CSS, and JavaScript. Enjoys creating intuitive and user-friendly interfaces that enhance user experience. Known for problem-solving abilities and meticulous attention to detail, ensuring high-quality, responsive designs.',
        3),

       (10, 'mariaslavova', 'password', 'Maria', 'Slavova', 'mariaslavova@mail.bg',
        'Operations manager with a deep understanding of logistics and supply chain management, dedicated to streamlining business operations and reducing costs. Experienced in process optimization, quality control, and team coordination. Known for excellent organizational skills and an analytical mindset.',
        4),

       (11, 'nikolaypetrov', 'password', 'Nikolay', 'Petrov', 'nikolaypetrov@mail.bg',
        'Content writer and editor with expertise in crafting engaging and informative content for digital platforms. Skilled in SEO, content strategy, and copywriting, with a passion for delivering valuable information to readers. Known for exceptional writing skills and a strong understanding of audience engagement.',
        5),

       (12, 'ivankolev', 'password', 'Ivan', 'Kolev', 'ivankolev@mail.bg',
        'Mechanical engineer specializing in automotive and manufacturing processes. Highly skilled in CAD design and experienced in coordinating large-scale engineering projects. Known for problem-solving capabilities and technical knowledge in materials and fabrication techniques.',
        6),

       (13, 'sofiyageorgieva', 'password', 'Sofiya', 'Georgieva', 'sofiyageorgieva@mail.bg',
        'Financial analyst with extensive experience in budgeting, forecasting, and financial reporting. Proficient in Excel, financial modeling, and data analysis. Known for analytical skills, attention to detail, and the ability to communicate complex financial information clearly.',
        3),

       (14, 'stoyankostov', 'password', 'Stoyan', 'Kostov', 'stoyankostov@mail.bg',
        'UX/UI designer with a background in psychology and a strong understanding of user-centered design principles. Skilled in wireframing, prototyping, and user testing, with experience in improving user engagement. Known for creativity, empathy, and the ability to balance aesthetics with functionality.',
        4),

       (15, 'viktorstoyanov', 'password', 'Viktor', 'Stoyanov', 'viktorstoyanov@mail.bg',
        'Sales manager with a proven track record in business development and team leadership. Known for persuasive communication skills and an ability to build strong client relationships. Skilled in developing sales strategies that align with business objectives and drive revenue growth.',
        5),

       (16, 'milenminkov', 'password', 'Milen', 'Minkov', 'milenminkov@mail.bg',
        'Product manager with expertise in agile methodologies and a strong technical background. Skilled in roadmap planning, feature prioritization, and cross-functional collaboration. Known for problem-solving abilities, customer-focused mindset, and the ability to lead teams through the product development lifecycle.',
        6),

       (17, 'ilianaivanova', 'password', 'Iliana', 'Ivanova', 'ilianaivanova@mail.bg',
        'HR consultant with a focus on organizational development and change management. Skilled in talent acquisition, training, and employee engagement. Known for a collaborative approach to problem-solving and a commitment to improving workplace culture.',
        3),

       (18, 'roberttodorov', 'password', 'Robert', 'Todorov', 'roberttodorov@mail.bg',
        'Legal advisor with expertise in corporate and contract law. Known for strong analytical skills, attention to detail, and the ability to provide sound legal counsel. Experienced in drafting and reviewing contracts, managing legal compliance, and supporting business transactions.',
        4),

       (19, 'venelinaangelova', 'password', 'Venelina', 'Angelova', 'venelinaangelova@mail.bg',
        'Supply chain analyst with a background in logistics and data analysis. Skilled in inventory management, demand forecasting, and supply chain optimization. Known for analytical abilities and an ability to find cost-effective solutions that improve efficiency.',
        5),

       (20, 'dimitarkarov', 'password', 'Dimitar', 'Karov', 'dimitarkarov@mail.bg',
        'Business analyst with expertise in process improvement and data-driven decision-making. Skilled in requirements gathering, documentation, and stakeholder communication. Known for a strong analytical mindset and problem-solving skills.',
        6),

       (21, 'nikolayvoynov', 'password', 'Nikolay', 'Voynov', 'nikolayvoynov@mail.bg',
        'Backend Engineer with expertise in system architecture, performance optimization, and API development. Skilled in coding, database management, and integration. Known for a strong technical mindset, problem-solving abilities, and delivering efficient, scalable solutions.',
        6);


-- job_ads Table --
insert into job_match.job_ads (job_ad_id, position_title, min_salary_boundary, max_salary_boundary, job_description,
                               location_id, company_id)
values (1, 'Software Engineer', 3500, 4000,
        'Dev Experts is seeking a highly motivated Software Engineer to join our team of innovative professionals. As a Software Engineer, you will be responsible for developing high-performance trading platforms and financial technology solutions for our global clients. The ideal candidate will have experience in Java and Python, with a deep understanding of system architecture and backend development. In this role, you will work closely with cross-functional teams to design, develop, and deploy software solutions that meet the specific needs of the financial industry.',
        2, 1),

       (2, 'Digital Marketing Manager', 3000, 3500,
        'Green Solutions is looking for a creative and strategic Digital Marketing Manager to drive online campaigns and enhance our digital presence. In this role, you will be responsible for developing and executing marketing strategies to promote Green Solutions’ services and expand our customer base. Your expertise in SEO, content marketing, and social media will be essential to the success of our campaigns. You will manage online ads, optimize our website, and use data-driven insights to improve customer acquisition and retention.',
        2, 2),

       (3, 'Project Manager', 4000, 4500,
        'Tech Innovators is seeking a Project Manager to join our team and help lead complex digital transformation projects. You will be responsible for overseeing multiple projects from initiation through to completion, ensuring that all deliverables meet quality standards and deadlines. You will work closely with technical teams, business stakeholders, and vendors to develop project plans, allocate resources, and ensure that each project aligns with the company’s strategic objectives.',
        5, 3),

       (4, 'UX/UI Designer', 3200, 3700,
        'Market Gurus is on the lookout for a talented UX/UI Designer to join our growing team. In this role, you will be responsible for designing intuitive and visually appealing user interfaces for web and mobile applications. Your role will involve creating wireframes, prototypes, and high-fidelity designs that enhance the user experience while aligning with the business goals of our clients. You will collaborate closely with developers and product managers to ensure that your designs are feasible, functional, and scalable.',
        6, 4),

       (5, 'Full-Stack Developer', 3500, 4000,
        'NextGen Health is looking for a talented Full-Stack Developer to join our team and help build innovative healthcare solutions. In this role, you will work on both front-end and back-end development, building applications that improve patient care and healthcare operations. You will be involved in designing, developing, and maintaining a variety of software tools, including patient management systems, electronic health record (EHR) software, and telemedicine platforms.',
        3, 5),

       (6, 'Sales Manager', 3500, 4000,
        'Eco Builders is seeking a dynamic and experienced Sales Manager to lead our sales team and drive growth in the sustainable construction market. In this role, you will be responsible for developing and implementing sales strategies, managing client relationships, and identifying new business opportunities. You will lead a team of sales professionals and work closely with other departments to ensure that sales efforts are aligned with company objectives. The ideal candidate will have a strong background in sales, preferably in the construction or environmental industries, and a deep understanding of sustainability and green building practices.',
        1, 6),

       (7, 'Customer Success Manager', 3200, 3700,
        'Edu Future is looking for a dedicated and enthusiastic Customer Success Manager to join our team and help deliver exceptional experiences to our clients. In this role, you will be responsible for building strong relationships with customers, understanding their needs, and ensuring they get the most out of our educational products. You will collaborate closely with sales, product, and support teams to ensure seamless customer onboarding and continued success throughout their journey with Edu Future.',
        5, 7),

       (8, 'Operations Analyst', 2800, 3300,
        'Agro Corp is seeking an Operations Analyst to join our team and help optimize our agricultural operations. In this role, you will be responsible for analyzing data, identifying inefficiencies, and implementing strategies to improve supply chain processes. You will work closely with other departments, including procurement, logistics, and production, to ensure smooth operations and support decision-making.',
        6, 8),

       (9, 'Data Analyst', 3200, 3700,
        'Dev Experts is seeking a skilled Data Analyst to join our growing team. In this role, you will be responsible for analyzing complex datasets to derive actionable insights that drive business decisions. You will work closely with business stakeholders to understand their objectives and provide data-driven recommendations. Your work will involve using tools such as SQL, Python, and Tableau to clean, process, and visualize data.',
        5, 1),

       (10, 'Financial Analyst', 3500, 4000,
        'Tech Innovators is looking for a talented Financial Analyst to support our team in making data-driven investment decisions. In this role, you will be responsible for preparing financial reports, analyzing market trends, and creating financial models to assist with forecasting and budgeting. You will collaborate with senior analysts and portfolio managers to provide insights on potential investment opportunities.',
        1, 3),

       (11, 'Web Developer', 3000, 3500,
        'Dev Experts is looking for an innovative Web Developer to join our team and help build and maintain cutting-edge websites and applications. As a Web Developer, you will be responsible for writing clean, efficient, and well-documented code that meets both business requirements and user needs. You will collaborate with designers and other developers to create user-friendly web experiences using modern front-end technologies such as HTML5, CSS3, JavaScript, and React.',
        2, 1),

       (12, 'Customer Support Specialist', 2500, 3000,
        'Retail Boost is looking for a passionate and dedicated Customer Support Specialist to help provide exceptional support to our clients. You will be the first point of contact for customers seeking help with our software products and services. You will respond to inquiries, resolve issues, and provide product training to ensure a seamless experience for users.',
        4, 9),

       (13, 'Business Development Manager', 4000, 4500,
        'Edu Future is seeking a Business Development Manager to lead our growth initiatives and build strategic relationships with clients and partners. In this role, you will identify new business opportunities, negotiate contracts, and manage key accounts to expand our client base. You will work closely with the marketing and sales teams to align strategies and ensure that the company is meeting its revenue goals.',
        6, 7),

       (14, 'HR Manager', 3500, 4000,
        'Green Solutions is looking for an experienced HR Manager to join our growing team. In this role, you will be responsible for overseeing all aspects of human resources, including recruitment, employee relations, performance management, and compliance with labor laws. You will work closely with leadership to implement HR strategies that align with the company’s business goals and ensure a positive and productive work environment.',
        2, 10),

       (15, 'Content Strategist', 3200, 3700,
        'TechVentures is looking for a Content Strategist to join our team and help create engaging content that aligns with our business objectives. As a Content Strategist, you will be responsible for developing and executing content plans that drive brand awareness, engage our audience, and support lead generation efforts. You will work closely with marketing, sales, and product teams to create content that resonates with our target market. The ideal candidate will have experience in content creation, SEO, and social media strategy.',
        4, 3),

       (16, 'Supply Chain Manager', 3800, 4300,
        'Global Logistics is seeking an experienced Supply Chain Manager to join our team and optimize our logistics operations. You will be responsible for overseeing the end-to-end supply chain, including procurement, inventory management, transportation, and distribution. You will collaborate with various teams to ensure that products are delivered to customers on time and within budget.',
        1, 10),

       (17, 'Marketing Specialist', 2700, 3200,
        'Eco Builders is looking for a Marketing Specialist to join our team and help drive our marketing initiatives. You will be responsible for executing marketing campaigns, creating content for digital and print media, and assisting with event coordination. The ideal candidate will have a strong background in marketing, excellent communication skills, and a creative approach to campaign development.',
        5, 6),

       (18, 'Java Developer', 3300, 3800,
        'Dev Solutions is seeking a skilled Java Developer to help build and maintain software applications for our clients. In this role, you will be responsible for developing, testing, and deploying Java-based applications that meet client needs. You will collaborate with cross-functional teams to ensure that projects are delivered on time and meet the highest quality standards.',
        2, 1),

       (19, 'Back-end engineer', 3000, 3500,
        'Tech Innovators is seeking a skilled and strategic Backend Engineer to join our development team and help build the foundation of our cutting-edge applications. In this role, you will be responsible for designing, implementing, and maintaining the backend architecture that powers our platforms.',
        2, 3);

-- job_applications Table --
insert into job_match.job_applications (job_application_id, min_desired_salary, max_desired_salary, motivation_letter,
                                        location_id, status_id, professional_id)
values (1, 3500, 4000,
        'Dear Hiring Manager, I am excited to apply for the Software Engineer position at Dev Experts. With my extensive background in software development, I believe I can contribute to your team in building high-performance trading platforms. Over the years, I have honed my skills in Java and Python, developing scalable and efficient applications that meet business needs.',
        3, 1,1),

       (2, 3000, 3500,
        'Dear Hiring Team, I am writing to express my interest in the Digital Marketing Manager role at Green Solutions. As a passionate marketer with over 5 years of experience in SEO, content marketing, and social media management, I am excited about the opportunity to contribute to Green Solutions’ mission of promoting sustainability.',
        4, 1,2),

       (3, 4000, 4500,
        'Dear Tech Innovators, I am thrilled to apply for the Project Manager position. With a background in managing complex digital transformation projects, I am confident that my skills and experience make me a strong candidate for this role.',
        5, 1,3),

       (4, 3200, 3700,
        'Dear Market Gurus, I am writing to express my interest in the UX/UI Designer position. As a passionate designer with a strong portfolio of work, I have spent the last few years designing user-centered interfaces for web and mobile applications.',
        6, 1,4),

       (5, 3500, 4000,
        'Dear NextGen Health, I am excited to apply for the Full-Stack Developer position. With a strong background in JavaScript, React, and Node.js, I have developed scalable applications that improve healthcare delivery and patient outcomes.',
        3, 1,4),

       (6, 3500, 4000,
        'Dear Eco Builders, I am writing to express my interest in the Sales Manager position. With over 7 years of experience in sales and a passion for sustainability, I am eager to help Eco Builders expand its reach and build lasting client relationships.',
        4, 4,15),

       (7, 3200, 3700,
        'Dear Edu Future, I am excited to apply for the Customer Success Manager position. As someone who is deeply passionate about education, I have dedicated my career to ensuring that students and professionals achieve their learning goals.',
        5, 4,7),

       (8, 2800, 3300,
        'Dear Agro Corp, I am writing to express my interest in the Operations Analyst position. I have a strong background in data analysis and business operations, and I am eager to bring my skills to Agro Corp. With my experience in analyzing supply chain processes and identifying efficiencies, I believe I can contribute to the optimization of your agricultural operations.',
        6, 4,20),

       (9, 3200, 3700,
        'Dear HR manager, I am writing to apply for the Data Analyst position. I have a strong background in data analysis and a deep understanding of business operations. I am highly skilled in tools such as SQL, Python, and Tableau, and I have experience working with large datasets to extract meaningful insights.',
        5, 1,20),

       (10, 3500, 4000,
        'Dear Future Investments, I am excited to apply for the Financial Analyst position. With my background in finance and experience in financial modeling, I am confident that I can make valuable contributions to your team. I have a strong understanding of financial analysis and have successfully built financial models that assist in forecasting and budgeting.',
        6, 4,20),

       (11, 3000, 3500,
        'Dear Hiring Manager, I am excited to apply for the Backend Engineer position at Tech Innovators. With my expertise in system architecture, performance optimization, and API development, I am confident in my ability to contribute effectively to your team and help build the foundation of your cutting-edge applications.',
        2, 1,21),

       (12, 2900, 4150,
        'Dear Hiring Manager, I am excited to apply for the Backend Engineer position at Tech Innovators. With my expertise in system architecture, performance optimization, and API development, I am confident in my ability to contribute effectively to your team and help build the foundation of your cutting-edge applications.',
        3, 4,21),

       (13, 3400, 4800,
        'Dear Hiring Manager, I am excited to apply for the Backend Engineer position at Tech Innovators. With my expertise in system architecture, performance optimization, and API development, I am confident in my ability to meet the demands of this role and contribute effectively to your team.',
        3, 5,2),

       (14, 3500, 4000,
        'Dear Hiring Manager, I am excited to apply for the Backend Engineer position at NextGen Health. With my expertise in system architecture, performance optimization, and API development, I am confident in my ability to meet the demands of this role and contribute effectively to your team.',
        3, 1,1);

-- job_ads_job_applications Table --

insert into job_match.job_ads_job_applications (job_ad_id, job_application_id)
values (1, 1),
       (1, 9),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (19, 11);



-- job_ads_requirements Table --

-- Job Ad 1: Software Engineer
-- Relevant requirements: Java, Python, System Architecture, Backend Development
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (1, 1),  -- Java
       (1, 2),  -- Python
       (1, 6),  -- SQL (likely needed for backend and database work)
       (1, 29), -- Critical Thinking
       (1, 7);
-- Attention to Detail

-- Job Ad 2: Digital Marketing Manager
-- Relevant requirements: SEO, Content Marketing, Social Media, Data Analysis
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (2, 27), -- Marketing
       (2, 4),  -- Communication Skills
       (2, 23), -- Customer Service
       (2, 15), -- Data Analysis
       (2, 5);
-- Problem-Solving

-- Job Ad 3: Project Manager
-- Relevant requirements: Project Management, Strategic Planning, Communication Skills
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (3, 3),  -- Project Management
       (3, 4),  -- Communication Skills
       (3, 24), -- Strategic Planning
       (3, 8),  -- Leadership
       (3, 29);
-- Critical Thinking

-- Job Ad 4: UX/UI Designer
-- Relevant requirements: Design, Creativity, Analytical Thinking, Attention to Detail
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (4, 22), -- Product Design
       (4, 7),  -- Attention to Detail
       (4, 13), -- Creativity
       (4, 12), -- Analytical Thinking
       (4, 4);
-- Communication Skills

-- Job Ad 5: Full-Stack Developer
-- Relevant requirements: Front-end and Back-end development, JavaScript, HTML, SQL
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (5, 2),  -- Python
       (5, 6),  -- SQL
       (5, 18), -- HTML
       (5, 29), -- Critical Thinking
       (5, 5);
-- Problem-Solving

-- Job Ad 6: Sales Manager
-- Relevant requirements: Sales Experience, Leadership, Customer Service, Negotiation Skills
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (6, 17), -- Sales Experience
       (6, 8),  -- Leadership
       (6, 23), -- Customer Service
       (6, 20), -- Negotiation Skills
       (6, 19);
-- Conflict Resolution

-- Job Ad 7: Customer Success Manager
-- Relevant requirements: Customer Service, Communication Skills, Teamwork, Problem-Solving
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (7, 23), -- Customer Service
       (7, 4),  -- Communication Skills
       (7, 9),  -- Teamwork
       (7, 5),  -- Problem-Solving
       (7, 30);
-- Emotional Intelligence

-- Job Ad 8: Operations Analyst
-- Relevant requirements: Data Analysis, Analytical Thinking, Attention to Detail
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (8, 15), -- Data Analysis
       (8, 12), -- Analytical Thinking
       (8, 7),  -- Attention to Detail
       (8, 5);
-- Problem-Solving

-- Job Ad 9: Data Analyst
-- Relevant requirements: Data Analysis, SQL, Python, Analytical Thinking
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (9, 15), -- Data Analysis
       (9, 6),  -- SQL
       (9, 2),  -- Python
       (9, 12), -- Analytical Thinking
       (9, 4);
-- Communication Skills

-- Job Ad 10: Financial Analyst
-- Relevant requirements: Financial Literacy, Analytical Thinking, Data Analysis
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (10, 21), -- Financial Literacy
       (10, 15), -- Data Analysis
       (10, 12), -- Analytical Thinking
       (10, 4);
-- Communication Skills

-- Job Ad 11: Web Developer
-- Relevant requirements: HTML, CSS, JavaScript, Communication Skills
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (11, 18), -- HTML
       (11, 7),  -- Attention to Detail
       (11, 4),  -- Communication Skills
       (11, 13);
-- Creativity

-- Job Ad 12: Customer Support Specialist
-- Relevant requirements: Customer Service, Communication Skills, Problem-Solving
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (12, 23), -- Customer Service
       (12, 4),  -- Communication Skills
       (12, 5),  -- Problem-Solving
       (12, 9);
-- Teamwork

-- Job Ad 13: Business Development Manager
-- Relevant requirements: Strategic Planning, Negotiation Skills, Customer Service, Communication Skills
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (13, 24), -- Strategic Planning
       (13, 20), -- Negotiation Skills
       (13, 23), -- Customer Service
       (13, 4);
-- Communication Skills

-- Job Ad 14: HR Manager
-- Relevant requirements: Communication Skills, Leadership, Conflict Resolution
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (14, 4),  -- Communication Skills
       (14, 8),  -- Leadership
       (14, 19), -- Conflict Resolution
       (14, 30);
-- Emotional Intelligence

-- Job Ad 15: Content Strategist
-- Relevant requirements: Marketing, Communication Skills, Creativity, Strategic Planning
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (15, 27), -- Marketing
       (15, 4),  -- Communication Skills
       (15, 13), -- Creativity
       (15, 24);
-- Strategic Planning

-- Job Ad 16: Supply Chain Manager
-- Relevant requirements: Budget Management, Strategic Planning, Communication Skills
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (16, 28), -- Budget Management
       (16, 24), -- Strategic Planning
       (16, 4),  -- Communication Skills
       (16, 5);
-- Problem-Solving

-- Job Ad 17: Marketing Specialist
-- Relevant requirements: Marketing, Communication Skills, Creativity
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (17, 27), -- Marketing
       (17, 4),  -- Communication Skills
       (17, 13);
-- Creativity

-- Job Ad 18: Java Developer
-- Relevant requirements: Java, Problem-Solving, Attention to Detail
insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (18, 1), -- Java
       (18, 5), -- Problem-Solving
       (18, 7); -- Attention to Detail

insert into job_match.job_ads_requirements (job_ad_id, requirement_id)
values (19, 1),  -- Java
       (19, 2),  -- Python
       (19, 6),  -- SQL (likely needed for backend and database work)
       (19, 29), -- Critical Thinking
       (19, 7);
-- Attention to Detail


-- job_applications_job_ads Table --

insert into job_match.job_applications_job_ads (job_application_id, job_ad_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);


-- job_applications_skills Table --

-- Application 1: Software Engineer (Java, Python, Software Development)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (1, 1),  -- Java
       (1, 2),  -- Python
       (1, 5),  -- Problem-Solving
       (1, 37), -- Coding Best Practices
       (1, 12);
-- Analytical Thinking

-- Application 2: Digital Marketing Manager (SEO, Content Marketing, Social Media)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (2, 27), -- Marketing
       (2, 46), -- Social Media Management
       (2, 49), -- Search Engine Optimization (SEO)
       (2, 10), -- Time Management
       (2, 50);
-- Content Creation

-- Application 3: Project Manager (Project Management, Digital Transformation)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (3, 3),  -- Project Management
       (3, 8),  -- Leadership
       (3, 24), -- Strategic Planning
       (3, 26), -- Agile Methodologies
       (3, 29);
-- Critical Thinking

-- Application 4: UX/UI Designer (User Interface Design)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (4, 41), -- UX/UI Design
       (4, 13), -- Creativity
       (4, 25), -- Presentation Skills
       (4, 36), -- Organization Skills
       (4, 5);
-- Problem-Solving

-- Application 5: Full-Stack Developer (JavaScript, React, Node.js)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (5, 37), -- Coding Best Practices
       (5, 1),  -- Java
       (5, 18), -- HTML
       (5, 6);
-- SQL

-- Application 6: Sales Manager (Sales Experience, Client Management)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (6, 17), -- Sales Experience
       (6, 14), -- Client Management
       (6, 20), -- Negotiation Skills
       (6, 31), -- Networking
       (6, 54);
-- Interpersonal Skills

-- Application 7: Customer Success Manager (Customer Service, Relationship Management)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (7, 23), -- Customer Service
       (7, 40), -- Customer Relationship Management (CRM)
       (7, 32), -- Self-Motivation
       (7, 9),  -- Teamwork
       (7, 4);
-- Communication Skills

-- Application 8: Operations Analyst (Data Analysis, Business Operations)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (8, 15), -- Data Analysis
       (8, 39), -- Statistical Analysis
       (8, 52), -- Supply Chain Management
       (8, 55), -- Process Improvement
       (8, 12);
-- Analytical Thinking

-- Application 9: Data Analyst (SQL, Python, Tableau, Data Analysis)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (9, 6),  -- SQL
       (9, 2),  -- Python
       (9, 15), -- Data Analysis
       (9, 44), -- Data Visualization
       (9, 39);
-- Statistical Analysis

-- Application 10: Financial Analyst (Finance, Budgeting, Financial Analysis)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (10, 21), -- Financial Literacy
       (10, 6),  -- SQL
       (10, 28), -- Budget Management
       (10, 29), -- Critical Thinking
       (10, 51); -- Risk Management

-- Application 11: Financial Analyst (Finance, Budgeting, Financial Analysis)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (11, 1),  -- Java
       (11, 2),  -- Python
       (11, 5),  -- Problem-Solving
       (11, 37), -- Coding Best Practices
       (11, 12);-- Analytical Thinking

-- Application 12: Financial Analyst (Finance, Budgeting, Financial Analysis)
insert into job_match.job_applications_skills (job_application_id, skill_id)
values (12, 1),  -- Java
       (12, 2),  -- Python
       (12, 6);  -- SQL
#        (12, 29), -- Critical Thinking
#        (12, 7);  -- Attention to detail

insert into job_match.job_applications_skills (job_application_id, skill_id)
values (14, 2),  -- Java
       (14, 5),  -- Problem-Solving
       (14, 6), -- SQL
       (14, 18),-- HTML
       (14, 29);-- Critical Thinking

-- matches Table --

insert into job_match.matches (match_id, job_ad_id, job_application_id)
values (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 5),
       (6, 19, 11);