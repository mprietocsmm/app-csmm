from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import TokenFcm
from csmm_app.endpoints.funciones import *
import json, datetime, config
import firebase_admin
from firebase_admin import credentials, messaging

@csrf_exempt
def token(request):
    if request.method == 'POST':
        body = json.loads(request.body)
        try:
            token_sesion = body['tokenSesion']
            token_fcm = body['tokenFCM']
            tipo = body['tipoUsuario']
        except KeyError:
            return JsonResponse({}, status=400)
        
        usuario = busqueda_usuario_token_tipo(token_sesion, tipo)
        registro = TokenFcm(token=token_fcm, id_usuario=usuario.id, tipo=tipo, fecha=datetime.datetime.now())
        registro.save()
        return JsonResponse({}, status=200)
    elif request.method == 'DELETE':
        body = json.loads(request.body)
        try:
            token_fcm = body['tokenFCM']
        except KeyError:
            return JsonResponse({}, status=400)
        
        TokenFcm.objects.get(token=token_fcm).delete()
    else:
        return JsonResponse({}, status=405)
    

def comprobar(request):
    tokens_list = []
    for token in TokenFcm.objects.all():
        tokens_list.append(token.token)

    message = messaging.MulticastMessage(
        android=messaging.AndroidConfig(
            ttl=datetime.timedelta(seconds=3600),
            priority='normal',
            notification=messaging.AndroidNotification(
                priority='min',
                channel_id='Comunicaciones'
            ),
            data={
                "mensaje": "hoola"
            },
        ),
        tokens=tokens_list,
    )

    response = messaging.send_multicast(message)
    if response.failure_count > 0:
        responses = response.responses
        failed_tokens = []
        for idx, resp in enumerate(responses):
            if not resp.success:
                failed_tokens.append(tokens_list[idx])
        print('List of tokens that caused failures: {0}'.format(failed_tokens))

        for token in failed_tokens:
            TokenFcm.objects.get(token=token).delete()
    return JsonResponse({"mensaje": "ok"}, safe=False, status=200)