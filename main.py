import vk
import re
import psycopg2

import os
import datetime as dt
import requests
import pandas as pd
from airflow.models import DAG
from airflow.operators.python_operator import PythonOperator

def parser():

    session = vk.Session(
        access_token='7d47945cb52ffd2dc3c080f16d3e58183c41cf8b437b9201bfe6ac2628a97f9f3ef9f31d3411f916310aa')
    vk_api = vk.API(session, v='5.85')
    posts = vk_api.wall.get(owner_id=-35488145, domain='https://vk.com/itis_kfu', filter='owner', count=100)
    posts2 = vk_api.wall.get(owner_id=-35488145, domain='https://vk.com/itis_kfu', filter='owner', count=100, offset=100)
    posts['items'] += posts2['items']
    s = ""
    for i in range(200):
        s += posts['items'][i]['text']


    mt_list = s.split()

    my_map = {}

    for i in mt_list:
        my_map[i] = my_map.get(i, 0) + 1
    map2 = {}

    for i in my_map:
        z = my_map.get(i)
        e = []
        e.append(i)
        map2[z] = e + map2.get(z, [])
    z = sorted(map2)
    for i in sorted(map2, reverse=True):
        print(i, map2[i])

    conn = psycopg2.connect(dbname='postgres', user='postgres',
                            password='mrroot7373', host='db-course.ce33vk2diesk.us-east-1.rds.amazonaws.com', port=5432)
    cursor = conn.cursor()

    cursor.execute('drop table word_count');
    cursor.commit()

    create_table_query = '''CREATE TABLE word_count
                              (word varchar,
                              count int); '''
    cursor.execute(create_table_query)
    conn.commit()

    count = 100

    for i in sorted(map2, reverse=True):
        if count < 0:
            break
        for j in range(len(map2[i])):
            count -= 1
            if count < 0:
                break
            sql_insert = 'insert into word_count values (\'' + map2[i][j] + '\', ' + (str)(i) + ');'
            cursor.execute(sql_insert)

    conn.commit()

with DAG(dag_id='titanic_pivot', default_args=args, schedule_interval=None) as dag:
    create_parser = PythonOperator(
        task_id='parser',
        python_callable=parser,
        dag=dag
    )
