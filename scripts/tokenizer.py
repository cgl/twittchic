from __future__ import print_function
import codecs
import re
import sys
import nltk


def tokenize():
    inFile = codecs.open(sys.argv[1],encoding = 'utf-8')
    outFile = open(sys.argv[2],'w+')
    upattern = u"(\.)+|(http://|https://|www.)[^\"\'\s\\#]+|[#@\\w']+|[.,!?;)(]+"
    # tokens will be 
    tokens = []
    for line in inFile:
        endLinesubs = line.replace("\\n","")
        tokens.append(nltk.tokenize.regexp_tokenize(endLinesubs,pattern=upattern,flags=re.UNICODE | re.MULTILINE | re.DOTALL))

    for line in tokens:
        tweet_tokenized = " ".join(line).encode('utf8')
        print(tweet_tokenized, file=outFile)

if len(sys.argv) == 3:
    tokenize()
else:
    print('Usage: python ./scripts/tokenizer.py resources/input/test.txt resources/input/tokenized.txt')
            
