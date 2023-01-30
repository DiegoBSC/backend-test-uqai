
INSERT INTO sec_users (created_at, updated_at, "username", email, "password", status)
	   VALUES(now(), now(), 'Admin', 'demo-2@gmail.com',
	  '$2a$10$gRde6y2LNyQwT6rVqem3qugYCqKDwgsqP/N1D2DHWr7tO2jupzLtG', 'ACT'::character varying);