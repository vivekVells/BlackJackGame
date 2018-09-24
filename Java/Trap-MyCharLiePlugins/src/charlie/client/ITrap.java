/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.client;

import charlie.message.Message;

/**
 *
 * @author maristuser
 */
interface ITrap {
    public void onReceive(Message message);
    public void onSend(Message message);    
}
