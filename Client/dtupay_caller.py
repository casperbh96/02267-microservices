import requests
import json

class DtuPayCaller():

    def __init__(self):
        self.customer_url = 'http://localhost:8081/customer'
        self.token_url = 'http://localhost:8082/token'
        self.merchant_url = 'http://localhost:8084/merchant'

    def createCustomer(self, customer_json):
        response = requests.post(self.customer_url, json=customer_json)

        if response.status_code in (200, 202):
            customer = json.loads(response.text)
            print('Customer: ' + str(customer))

            return customer
        else:
            print('Setup failed')
            print(response.status_code)
            print(response.text)

    def createMerchant(self, merchant_json):
        response = requests.post(self.merchant_url, json=merchant_json)

        if response.status_code in (200, 202):
            merchant = json.loads(response.text)
            print('Merchant: ' + str(merchant))

            return merchant
        else:
            print('Setup failed')
            print(response.status_code)
            print(response.text)

    def createTokens(self, token_json):
        response = requests.post(self.token_url, json=token_json)

        if response.status_code in (200, 202):
            token = json.loads(response.text)
            print('Token: ' + str(token))

            return token
        else:
            print('Setup failed')
            print(response.status_code)
            print(response.text)

    def updateMerchant(self, merchant_id, name, cvr):
        merchant_json = {
            'id': str(merchant_id),
            'name': name,
            'cvr': str(cvr)
        }

        response = requests.post(self.merchant_url, json=merchant_json)

        if response.status_code in (200, 202):
            merchant = json.loads(response.text)
            print('Updated Merchant: ' + str(merchant))

            return merchant
        else:
            print('Update failed')
            print(response.status_code)
            print(response.text)