-- Insert sample users
INSERT INTO users (username, email, display_name, first_name, last_name, balance, created_at, updated_at) VALUES
('marcus_j', 'marcus.j@example.com', 'Marcus J.', 'Marcus', 'Johnson', 2124.75, NOW(), NOW()),
('sarah_k', 'sarah.k@example.com', 'Sarah K.', 'Sarah', 'Kim', 1856.30, NOW(), NOW()),
('alex_r', 'alex.r@example.com', 'Alex R.', 'Alex', 'Rodriguez', 3245.90, NOW(), NOW());

-- Insert sample squads
INSERT INTO squads (name, description, status, user_id, created_at, updated_at) VALUES
('The Underdogs', 'Betting on the underdog teams', 'ACTIVE', 1, NOW(), NOW()),
('Safe Picks', 'Conservative betting strategy', 'ACTIVE', 1, NOW(), NOW()),
('High Rollers', 'Big money, big risks', 'ACTIVE', 1, NOW(), NOW()),
('Week 8 Special', 'Focus on week 8 games', 'ACTIVE', 1, NOW(), NOW()),
('Fantasy Football', 'Based on fantasy performance', 'ACTIVE', 1, NOW(), NOW()),
('Playoff Prep', 'Preparing for playoffs', 'ACTIVE', 1, NOW(), NOW());

-- Insert sample games
INSERT INTO games (id, home_team, away_team, home_score, away_score, game_time, status, week, season, created_at, updated_at) VALUES
('game_001', 'Chiefs', 'Bills', 28, 24, '2024-10-15 20:00:00', 'COMPLETED', 8, '2024', NOW(), NOW()),
('game_002', 'Cowboys', 'Eagles', 21, 17, '2024-10-15 13:00:00', 'COMPLETED', 8, '2024', NOW(), NOW()),
('game_003', 'Packers', 'Vikings', 14, 31, '2024-10-15 16:00:00', 'COMPLETED', 8, '2024', NOW(), NOW()),
('game_004', '49ers', 'Rams', NULL, NULL, '2024-10-21 20:00:00', 'SCHEDULED', 9, '2024', NOW(), NOW()),
('game_005', 'Patriots', 'Jets', 7, 14, '2024-10-15 13:00:00', 'LIVE', 8, '2024', NOW(), NOW());

-- Insert sample bets
INSERT INTO bets (game_id, team_name, amount, odds, bet_type, status, potential_payout, actual_payout, user_id, squad_id, created_at, updated_at) VALUES
('game_001', 'Chiefs', 50.00, 1.85, 'MONEYLINE', 'WON', 92.50, 92.50, 1, 1, '2024-10-15 19:00:00', NOW()),
('game_002', 'Eagles', 75.00, 2.10, 'MONEYLINE', 'LOST', 157.50, 0.00, 1, 2, '2024-10-15 12:00:00', NOW()),
('game_003', 'Vikings', 100.00, 1.75, 'MONEYLINE', 'WON', 175.00, 175.00, 1, 1, '2024-10-15 15:00:00', NOW()),
('game_005', 'Jets', 25.00, 1.90, 'MONEYLINE', 'PENDING', 47.50, NULL, 1, 3, '2024-10-15 12:30:00', NOW()),
('game_004', '49ers', 150.00, 1.65, 'MONEYLINE', 'PENDING', 247.50, NULL, 1, 2, '2024-10-20 10:00:00', NOW());