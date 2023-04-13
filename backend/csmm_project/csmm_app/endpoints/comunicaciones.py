from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
import json

def comunicaciones(request):
    if request.method == 'POST':
        pass
    elif request.method == 'GET':
        pass
    else:
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)