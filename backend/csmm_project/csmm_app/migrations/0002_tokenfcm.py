# Generated by Django 4.1.7 on 2023-05-16 10:52

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('csmm_app', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='TokenFcm',
            fields=[
                ('id', models.IntegerField(auto_created=True, primary_key=True, serialize=False)),
                ('token', models.CharField(max_length=200, unique=True)),
                ('id_usuario', models.IntegerField()),
                ('tipo', models.IntegerField()),
                ('fecha', models.DateField()),
            ],
            options={
                'db_table': 'token_fcm',
                'managed': False,
            },
        ),
    ]
