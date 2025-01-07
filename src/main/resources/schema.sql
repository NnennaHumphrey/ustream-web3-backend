-- Users table
CREATE TABLE users (
                       id CHAR(36) PRIMARY KEY NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP,
                       profile_picture VARCHAR(255),
                       enabled BOOLEAN NOT NULL DEFAULT true,
                       role VARCHAR(50) NOT NULL,
                       is_verified BOOLEAN NOT NULL DEFAULT false
);

-- Videos table
CREATE TABLE videos (
                        id CHAR(36) PRIMARY KEY NOT NULL,
                        video_url VARCHAR(255) NOT NULL,
                        video_name VARCHAR(255) NOT NULL,
                        upload_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP,
                        description TEXT NOT NULL,
                        user_id CHAR(36),
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Likes table
CREATE TABLE likes (
                       id CHAR(36) PRIMARY KEY NOT NULL,
                       video_id CHAR(36) NOT NULL,
                       user_id CHAR(36) NOT NULL,
                       liked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP,
                       FOREIGN KEY (video_id) REFERENCES videos(id) ON DELETE CASCADE,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Comments table
CREATE TABLE comments (
                          id CHAR(36) PRIMARY KEY NOT NULL,
                          video_id CHAR(36) NOT NULL,
                          user_id CHAR(36) NOT NULL,
                          text TEXT NOT NULL,
                          commented_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP,
                          parent_comment_id CHAR(36),
                          FOREIGN KEY (video_id) REFERENCES videos(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (parent_comment_id) REFERENCES comments(id) ON DELETE SET NULL
);

-- Leaderboard table
CREATE TABLE leaderboard (
                             id CHAR(36) PRIMARY KEY NOT NULL,
                             user_id CHAR(36) NOT NULL,
                             score INT NOT NULL,
                             last_score_reset TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP,
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- OTP table
CREATE TABLE otp (
                     id CHAR(36) PRIMARY KEY NOT NULL,
                     user_id CHAR(36) NOT NULL,
                     email VARCHAR(255) NOT NULL,
                     otp_code VARCHAR(50) NOT NULL,
                     otp_expiry_date TIMESTAMP NOT NULL,
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP,
                     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Password Reset Tokens table
CREATE TABLE password_reset_tokens (
                                       id CHAR(36) PRIMARY KEY NOT NULL,
                                       user_id CHAR(36) NOT NULL,
                                       token VARCHAR(255) NOT NULL,
                                       expiry_date TIMESTAMP NOT NULL,
                                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP,
                                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Refresh Tokens table
CREATE TABLE refreshTokens (
                               id CHAR(36) PRIMARY KEY NOT NULL,
                               user_id CHAR(36) NOT NULL,
                               refresh_token VARCHAR(255) NOT NULL,
                               expiry_date TIMESTAMP NOT NULL,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
