-- カテゴリーマスターデータ
DELETE FROM categories;
INSERT INTO categories (name, description) VALUES
('食料費', '食事や食材に関する支出'),
('交通費', '電車、バス、タクシーなどの交通機関利用費'),
('光熱費', '電気、ガス、水道などの公共料金'),
('雑費', 'その他の日常的な支出'),
('医療費', '病院、薬局などでの医療関連費用'),
('娯楽費', '映画、旅行、趣味などのエンターテイメント費'),
('投資', '株式、投資信託などの投資関連'),
('住居費', '家賃、住宅ローンなど住居に関する費用'),
('保険料', '生命保険、損害保険などの保険料'),
('通信費', '携帯電話、インターネットなどの通信費'),
('日用品', '洗剤、ティッシュなどの日用品購入費');

-- 家計簿データ
DELETE FROM household_expenses;
INSERT INTO household_expenses (expense_date, category, category_id, amount, description) VALUES
('2025-06-15', '食料費', (SELECT id FROM categories WHERE name = '食料費'), 1500.00, 'スーパーでの買い物'),
('2025-07-16', '交通費', (SELECT id FROM categories WHERE name = '交通費'), 300.00, '電車代'),
('2025-07-17', '光熱費', (SELECT id FROM categories WHERE name = '光熱費'), 8000.00, '電気代'),
('2025-07-18', '食料費', (SELECT id FROM categories WHERE name = '食料費'), 2000.00, 'レストランでの食事'),
('2025-07-19', '雑費', (SELECT id FROM categories WHERE name = '雑費'), 500.00, '文房具'),
('2025-07-20', '医療費', (SELECT id FROM categories WHERE name = '医療費'), 3000.00, '病院での診察'),
('2025-07-21', '食料費', (SELECT id FROM categories WHERE name = '食料費'), 1200.00, 'コンビニ弁当'),
('2025-07-22', '交通費', (SELECT id FROM categories WHERE name = '交通費'), 500.00, 'バス代'),
('2025-08-23', '娯楽費', (SELECT id FROM categories WHERE name = '娯楽費'), 2500.00, '映画鑑賞'),
('2025-09-24', '食料費', (SELECT id FROM categories WHERE name = '食料費'), 1800.00, '食材購入');

-- 家計予算データ
DELETE FROM household_budgets;
INSERT INTO household_budgets (category, category_id, amount) VALUES
('投資', (SELECT id FROM categories WHERE name = '投資'), 70000.00),
('住居費', (SELECT id FROM categories WHERE name = '住居費'), 42000.00),
('食料費', (SELECT id FROM categories WHERE name = '食料費'), 30000.00),
('娯楽費', (SELECT id FROM categories WHERE name = '娯楽費'), 30000.00),
('保険料', (SELECT id FROM categories WHERE name = '保険料'), 5000.00),
('通信費', (SELECT id FROM categories WHERE name = '通信費'), 2000.00),
('日用品', (SELECT id FROM categories WHERE name = '日用品'), 1000.00);
