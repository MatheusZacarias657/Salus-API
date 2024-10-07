INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('request-scheduler-notification-queue', 'request-scheduler-notification-exchange');
INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('request-medicine-mqtt-notification-queue', 'request-medicine-mqtt-notification-exchange');
INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('mqtt-medicine-notification-response-queue', 'mqtt-medicine-notification-response-exchange');
INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('request-report-queue', 'request-report-exchange');
INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('report-response-queue', 'report-response-exchange');
INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('mqtt-listener-register-queue', 'mqtt-listener-register-exchange');
INSERT INTO rabbitmq_queue (Queue, Exchange) VALUES ('mqtt-generic-notification-queue', 'mqtt-generic-notification-exchange');