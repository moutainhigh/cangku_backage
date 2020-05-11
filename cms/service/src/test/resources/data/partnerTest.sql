delete from `partner` where 1=1;
INSERT INTO `partner` (`id`, `name`, `address`, `descs`, `bank_account`, `bank_name`, `bank_address`, `contact_name`, `contact_phone`, `contact_email`, `ip_white_list`, `server_notice_url`, `appkey`, `appsecret`, `create_time`, `update_time`, `state`, is_delete) VALUES (1, '涠洲岛2', '地址', '介绍', '', '', '', '石斋', '1501111111', '', '', '', '86b9916deafe4a69a4284447da1d818f', 'fc541db806cc4148a324b58acd068d60', '2019-12-04 17:42:30', '2019-12-04 17:42:29', 0, 0);

delete from application where 1=1;
INSERT INTO application (`id`, `partner_id`, `client_type`, app_id) VALUES (1, 1, 1, '955622565');


