import requests
import json

class DtuPayCaller():

    def __init__(self):
        self.customer_url = 'http://localhost:8080/customer'
        self.tokens_url = 'http://localhost:8080/newTokens'
        self.merchant_url = 'http://localhost:8080/merchant'
        self.transaction_url = 'http://localhost:8080/transaction'

    def getCustomer(self):
        customer = {'id': 13, 'cpr': '999999', 'name': 'Manolo', 'tokens': []}
        print('Customer: ' + str(customer))
        
        return customer

    def getMerchant(self):
        merchant = {'id': 13, 'cvr': '1234', 'name': 'UpdatedMerchant'}
        print('Merchant: ' + str(merchant))
        
        return merchant

    def createTokens(self, token_json):
        response = requests.post(self.tokens_url, json=token_json)

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

        response = requests.put(self.merchant_url, json=merchant_json)

        if response.status_code in (200, 202):
            merchant = json.loads(response.text)
            print('Updated Merchant: ' + str(merchant))

            return merchant
        else:
            print('Update failed')
            print(response.status_code)
            print(response.text)

    def makeTransaction(self, merchant_id, single_token, amount, description):
        transaction_json = {
            'merchantId': str(merchant_id),
            'token': single_token,
            'amount': str(amount),
            'description': description
        }

        print(json.dumps(transaction_json))
        
        response = requests.post(self.transaction_url, json=transaction_json)

        if response.status_code in (200, 202):
            print('Made Transaction!!!')
        else:
            print('Transaction failed')
            print(response.status_code)
            print(response.text)
        
        