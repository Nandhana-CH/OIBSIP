import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score

df = pd.read_csv("iris.csv")  

df = df.drop(columns=["Id"])

print("Dataset Preview:")
print(df.head())
print("\nColumn Names:", df.columns)

df["Species"] = df["Species"].str.replace("Iris-", "", regex=True)

label_encoder = LabelEncoder()
df["Species"] = label_encoder.fit_transform(df["Species"])

X = df.drop(columns=["Species"])  
y = df["Species"] 

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

y_pred = model.predict(X_test)

accuracy = accuracy_score(y_test, y_pred)
print(f"\nModel Accuracy: {accuracy:.2f}")

sample = [[5.0, 3.4, 1.5, 0.2]]  
prediction = model.predict(sample)
predicted_species = label_encoder.inverse_transform(prediction)
print(f"\nPredicted Species for {sample}: {predicted_species[0]}")
