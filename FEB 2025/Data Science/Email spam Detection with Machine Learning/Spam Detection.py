import pandas as pd
import numpy as np
import re
import string
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix

df = pd.read_csv("spam.csv", encoding="ISO-8859-1") 

df = df.iloc[:, :2]  
df.columns = ["Label", "Message"]  

df["Label"] = df["Label"].map({"ham": 0, "spam": 1})

def clean_text(text):
    text = text.lower()  
    text = re.sub(r'\d+', '', text)  
    text = text.translate(str.maketrans('', '', string.punctuation)) 
    text = re.sub(r'\s+', ' ', text).strip() 
    return text

df["Cleaned_Message"] = df["Message"].apply(clean_text)

X_train, X_test, y_train, y_test = train_test_split(df["Cleaned_Message"], df["Label"], test_size=0.2, random_state=42)

vectorizer = TfidfVectorizer(stop_words="english")
X_train_tfidf = vectorizer.fit_transform(X_train)
X_test_tfidf = vectorizer.transform(X_test)

model = MultinomialNB()
model.fit(X_train_tfidf, y_train)

y_pred = model.predict(X_test_tfidf)

accuracy = accuracy_score(y_test, y_pred)
print(f"\n🔹 Model Accuracy: {accuracy:.2f}\n")
print("\nClassification Report:\n", classification_report(y_test, y_pred))

plt.figure(figsize=(5, 4))
sns.heatmap(confusion_matrix(y_test, y_pred), annot=True, fmt="d", cmap="Blues", xticklabels=["Ham", "Spam"], yticklabels=["Ham", "Spam"])
plt.xlabel("Predicted")
plt.ylabel("Actual")
plt.title("Confusion Matrix")
plt.show()

sample_email = ["Congratulations! You have won a free lottery ticket! Claim now!"]
sample_email_tfidf = vectorizer.transform(sample_email)
prediction = model.predict(sample_email_tfidf)
print("\n🔹 Prediction for sample email:", "Spam" if prediction[0] == 1 else "Ham")
