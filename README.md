CA1819-PathHunt

# Conveyor by Keyoti
Wij hebben de tool 'Conveyor' gebruikt om IIS Express bereikbaar te maken op het lokale netwerk zodat we onze web applicatie gemakkelijk kunnen testen vanaf een andere computer of smartphone. De stappen die je moet ondernemen om de tool te gebruiken zijn als volgt:

Link: https://marketplace.visualstudio.com/items?itemName=vs-publisher-1448185.ConveyorbyKeyoti

* Download de Visual Studio Exentension door Conveyor op te zoeken in de toolbar bij Tools -> Extensions and Updates <br>
* Je moet een regel voor binnenkomende verbindingen toevoegen in je firewall om toegang tot de TCP poort van Remote URL te geven.
    * Zoek bij windows naar 'WF.msc'
    * Klik op 'regels voor binnenkomende verbindingen'
    * Klik op 'nieuwe regel'
    * Klik op 'Poort' en dan op volgende
    * Selecteer TCP en zet hier vervolgens de poort van de Remote URL in het vak 'Specifieke lokale poorten'. Dit is waarschijnlijk poort 45455, druk dan op volgende. Deze kan je ook vinden door je web applicatie te starten wanneer conveyor is geïnstalleerd. Conveyor wordt automatisch gestart voor web applicaties.
    * Druk dan op volgende en vervolgens nog eens op volgende (Je kan 'Public' uitzetten), en geef het vervolgens een naam bv. 'Conveyor: web dev server access enabled'.
* Zoek vervolgens in het bestand 'applicationhost.config', dat standaard in de map Documenten/IISExpress/config te vinden is, naar de volgende regel: <br> <binding protocol&equals;"http" bindingInformation&equals;":8080:localhost"/> en verander het naar <br> 
<binding protocol&equals;"http" bindingInformation&equals;"&ast;:8080:&ast;" 
* Start Visual Studio nu op met administrator rechten en nu zou je het IP met de bijbehorende poort (waarschijnlijk 45455) van je desktop/laptop moeten kunnen ingeven om je applicatie te bereiken.
