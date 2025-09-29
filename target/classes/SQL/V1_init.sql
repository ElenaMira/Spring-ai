create table if not exists spring_ai_chat_memory (
  conversation_id varchar(255) not null,
  content TEXT not null,
  type VARCHAR(10) not null,
  timestamp TIMESTAMP not null,
  INDEX SPRING_AI_MEMORY_IDX_TIMESTAMP (conversation_id, timestamp)
);