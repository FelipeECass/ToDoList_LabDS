DROP TABLE IF EXISTS task;

CREATE TABLE task (
    task_Id INT AUTO_INCREMENT PRIMARY KEY,
    task_Title varchar(30) NOT NULL,
    task_Desc varchar(250),
    task_Status tinyint NOT NULL
);

INSERT INTO task(task_Title, task_Desc, task_Status) VALUES
('Test01', 'Test01Desc', '0'),
('Test02', 'Test02Desc', '1');