CREATE TABLE faq
(
    Id INT PRIMARY KEY IDENTITY(1,1),
    Group_Id INT NOT NULL,
    Title VARCHAR(50) NOT NULL,
    Text VARCHAR(1000) NOT NULL,

    constraint fk_faq_groupfaq foreign key (Group_Id) references group_faq(id),
);