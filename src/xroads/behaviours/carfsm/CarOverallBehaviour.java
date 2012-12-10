package xroads.behaviours.carfsm;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Celkove chovani auta implementovane formou FSM
 * 
 * Je treba davat pozor na to, ze stavy se vytvori pouze jednou 
 * a pote se pouzivaji jiz existujici instance. Proto je nutne 
 * pro spravnou funkcnost implementovat v metode onStart kazdeho 
 * stavu reset jeho vnitrnich promennych.
 */
@SuppressWarnings("serial")
public class CarOverallBehaviour extends FSMBehaviour {

	private static final String STATE_WAIT_QUEUE = "wait in queue";
	private static final String STATE_CHOOSE_DIR = "choosing direction";
	private static final String STATE_WAIT_GREEN = "wait for green light";
	private static final String STATE_ENQUEUE = "enqueue";
	private static final String STATE_QUEUE_DELAY = "queue delay";
	private static final String STATE_AT_ENDPOINT = "endpoint";




	public CarOverallBehaviour() {
		super();

		registerFirstState(new WaitInQueueBehaviour(), STATE_WAIT_QUEUE);
		registerState(new ChooseDirectionBehaviour(), STATE_CHOOSE_DIR);
		registerState(new WaitForGreenBehaviour(), STATE_WAIT_GREEN);
		registerState(new EnqueueBehaviour(), STATE_ENQUEUE);
		registerState(new QueueDelayBehaviour(), STATE_QUEUE_DELAY);
		registerLastState(leaveToEndpoint, STATE_AT_ENDPOINT);

		registerDefaultTransition(STATE_WAIT_QUEUE, STATE_CHOOSE_DIR, new String[] { STATE_WAIT_QUEUE });
		registerDefaultTransition(STATE_CHOOSE_DIR, STATE_WAIT_GREEN, new String[] { STATE_CHOOSE_DIR });
		registerDefaultTransition(STATE_WAIT_GREEN, STATE_ENQUEUE, new String[] { STATE_WAIT_GREEN });
		registerDefaultTransition(STATE_QUEUE_DELAY, STATE_WAIT_QUEUE);

		// zarazeni do fronty se bude opakovat dokud se nepovede
		// FIXME: pozor, auto v tomto stavu jiz nekontroluje zelenou na semaforu!
		registerTransition(STATE_ENQUEUE, STATE_ENQUEUE, EnqueueBehaviour.ENQUEUE_FAILED);

		// presun do dalsi krizovatky, kde soucasne probiha zpozdeni simulujici prijezd k ni
		registerTransition(STATE_ENQUEUE, STATE_QUEUE_DELAY, EnqueueBehaviour.ENQUEUE_OK, new String[] { STATE_ENQUEUE });

		// presun do ciloveho stavu v pripade ze se auto zaradi do fronty koncovky (bez zpozdeni)
		registerTransition(STATE_ENQUEUE, STATE_AT_ENDPOINT, EnqueueBehaviour.ENQUEUE_ENDPOINT, new String[] { STATE_ENQUEUE });
	}




	/**
	 * Tento stav nic nedela, jen symbolizuje ze fronta do ktere se zaradilo 
	 * patri koncovce a tudiz je FSM v koncovem stavu
	 */
	private OneShotBehaviour leaveToEndpoint = new OneShotBehaviour() {
		@Override
		public void action() {
			System.out.println("Car is at endpoint");

			// do nothing
		}
	};



}
