#include "pch.h"
#include <iostream>
#include <stdlib.h>
#include <jni.h>
#include "lab11.h"

using namespace std;

JNIEXPORT jobjectArray JNICALL Java_statistics_Chi2_calculate0(JNIEnv * env, jobject obj, jobjectArray arr, jobjectArray arr2) {
	jobject doubleObject = env->GetObjectArrayElement(arr, 0);
	int length = env->GetArrayLength(arr);
	jclass doubleClass = env->GetObjectClass(doubleObject);
	jmethodID doubleValue = env->GetMethodID(doubleClass, "doubleValue", "()D");
	jobjectArray result = env->NewObjectArray(length, doubleClass, NULL);

	double *arrObs = new double[length];
	double *arrExp = new double[length];

	for (int i = 0; i < length; i++) {
		doubleObject = env->GetObjectArrayElement(arr, i);
		arrObs[i] = env->CallDoubleMethod(doubleObject, doubleValue);
		doubleObject = env->GetObjectArrayElement(arr2, i);
		arrExp[i] = env->CallDoubleMethod(doubleObject, doubleValue);
	}

	jmethodID constructor = env->GetMethodID(doubleClass, "<init>", "(D)V");

	for (int i = 0; i < length; i++) {
		double tempResult = (pow((arrObs[i] - arrExp[i]), 2)/arrExp[i]);
		env->SetObjectArrayElement(result, i, env->NewObject(doubleClass, constructor, tempResult));
	}

	delete[] arrObs;
	delete[] arrExp;

	return result;
}

JNIEXPORT void JNICALL Java_statistics_Chi2_calculate___3Ljava_lang_Double_2(JNIEnv * env, jobject obj, jobjectArray arr) {
	jobject doubleObject = env->GetObjectArrayElement(arr, 0);
	unsigned int length = env->GetArrayLength(arr);
	jclass doubleClass = env->GetObjectClass(doubleObject);
	jmethodID doubleValue = env->GetMethodID(doubleClass, "doubleValue", "()D");
	double* arrExp = new double[length];
	double* arrObs = new double[length];

	jclass jc = env->GetObjectClass(obj);
	jfieldID field = env->GetFieldID(jc, "expected", "[Ljava/lang/Double;");
	jobjectArray expectedArray = (jobjectArray)env->GetObjectField(obj, field);
	jobject arrObj = env->GetObjectArrayElement(expectedArray, false);

	for (int i = 0; i < length; i++) {
		arrObj = env->GetObjectArrayElement(expectedArray, i);
		arrExp[i] = env->CallDoubleMethod(arrObj, doubleValue);
		doubleObject = env->GetObjectArrayElement(arr, i);
		arrObs[i] = env->CallDoubleMethod(doubleObject, doubleValue);
	}

	cout << "WYNIKI:" << endl;
	for (int i = 0; i < length; i++) {
		double tempResult = (pow((arrObs[i] - arrExp[i]), 2) / arrExp[i]);
		cout << tempResult << " ";
	}

	delete[] arrExp;
	delete[] arrObs;
}

JNIEXPORT jobjectArray JNICALL Java_statistics_Chi2_calculate__(JNIEnv * env, jobject obj) {
	int numberOfNumbers;
	cout << "Podaj liczbe wprowadzanych liczb: ";
	cin >> numberOfNumbers;

	jclass doubleClass = env->FindClass("java/lang/Double");
	jobjectArray result = env->NewObjectArray(numberOfNumbers,doubleClass , NULL);;
	double *arrObs = new double[numberOfNumbers];
	double *arrExp = new double[numberOfNumbers];
	double tempNumber;

	cout << "Wprowadz obserwowane dane w formule [liczba][enter][liczba].." << endl;
	for (int i = 0; i < numberOfNumbers; i++) {
		cin >> tempNumber;
		arrObs[i] = tempNumber;
	}

	cout << "Wprowadz oczekiwane dane w formule [liczba][enter][liczba].." << endl;
	for (int i = 0; i < numberOfNumbers; i++) {
		cin >> tempNumber;
		arrExp[i] = tempNumber;
	}

	jmethodID constructor = env->GetMethodID(doubleClass, "<init>", "(D)V");

	for (int i = 0; i < numberOfNumbers; i++) {
		double tempResult = (pow((arrObs[i] - arrExp[i]), 2) / arrExp[i]);
		env->SetObjectArrayElement(result, i, env->NewObject(doubleClass, constructor, tempResult));	
	}

	delete[] arrObs;
	delete[] arrExp;

	return result;
}

