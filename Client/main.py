from time import sleep
import os
import sys
from random import randrange
import requests
import json
import uuid
from dtupay_caller import DtuPayCaller

caller = DtuPayCaller()

create_customer_json = {
    'cpr': str(randrange(9999999999)),
    'name': 'Test Customer Client'
}

create_merchant_json = {
    'cvr': str(randrange(9999999999)),
    'name': 'Test Merchant Client'
}

def setup():
    print('Setting up the client... (wait)\n')
    
    customer = caller.createCustomer(create_customer_json)
    merchant = caller.createMerchant(create_merchant_json)

    create_token_json = {
        'customerId': customer['id'],
        'numberOfTokens': str(1)
    }

    token = caller.createTokens(create_token_json)

    print('\nSetup done: created customer, merchant and token')

    return customer, merchant, token


def display_title_bar():
    print("\n\t**********************************************")
    print("\t***  02267 Microservices Client (Merchant) ***")
    print("\t**********************************************\n")

display_title_bar()
customer, merchant, token = setup()

last_input = ''

while last_input != "q":
    print('\nYour options are')
    print('q: Exit the program                                      \n' + 
          '1: Update the merchant                                   \n' +
          '2: Transfer money from customer to merchant              \n' )
    
    last_input = input()

    if last_input == '0':
        sys.exit('You stopped the program')
    elif last_input == '1':
        merchant_id = merchant['id']
        name = input('Please input updated name: ')
        cvr = input('Please input updated CVR: ')

        merchant = caller.updateMerchant(merchant_id, name, cvr)

    elif last_input == '2':
        print('Making transaction')
