# BDC
Nel caricamento dei file va tolto l'header.

Nel caricamento dei flussi se la galassia cui si riferisce il flusso non è registrata allora il flusso non viene registrato
Riguardo ciò abbiamo notato che due righe di flussi non vengono salvate, nello specifico si riferiscono alle galassie
IRASF06361-621
AM0702-601NED0
Penso che manchi un ultima lettera nel nome di tali galassie perche nel file MRTable3 ho trovato le galassie
IRASF06361-6217
AM0702-601NED02
Dunque nei nostri file locali abbiamo corretto questo supposto errore di battitura chiamando le galassie IRASF06361-6217 e AM0702-601NED02.

Per i flussi riga pacs c'è un flusso riferito alla galassia NGC3607 e tale galassia non esiste nel file delle galassie quindi non viene salvato tale flusso