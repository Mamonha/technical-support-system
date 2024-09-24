package app.ticket;

import app.dto.Ticket.RequestTicket;
import app.dto.Ticket.ResponseTicket;
import app.dto.Ticket.TicketSimplified;
import app.entities.Category;
import app.entities.Ticket;
import app.entities.User;
import app.enums.Priority;
import app.enums.TicketStatus;
import app.repositories.CategoryRepository;
import app.repositories.TicketRepository;
import app.repositories.UserRepository;
import app.services.ResponseService;
import app.services.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceTicketTest {

    @InjectMocks
    private TicketService ticketService; // Injetando o serviço que será testado

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ResponseService responseService;

    @BeforeEach
    void setup() {
        // Inicializar os mocks
        MockitoAnnotations.openMocks(this);

        // Configuração de mocks para os testes
        User user = new User(1L, "Mamonha", "mamonha@gmail.com", "45 99999-4565", "8018742939", 1, new ArrayList<>(), new ArrayList<>());
        Category category = new Category(1L, "General","description", new ArrayList<>());

        // Mocking the user repository
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Mocking the category repository
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Mockito.when(categoryRepository.findAllById(Mockito.anyList())).thenReturn(categories);

        // Mocking the ticket repository
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved ticket
        Mockito.when(ticketRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Ticket()));
    }

    @Test
    void openTest() {
        RequestTicket requestTicket = new RequestTicket();
        requestTicket.setUserId(1L);
        requestTicket.setTitle("titulo");
        requestTicket.setDescription("descripção ticket");
        requestTicket.setPriority(Priority.HIGH);
        requestTicket.setUserId(1L);
        requestTicket.setCategoryIds(Arrays.asList(1L));

        Ticket ticket = new Ticket();
        ticket.setDateTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.OPEN.getValue());
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.open(requestTicket);

        assertEquals(TicketStatus.OPEN.getValue(), result.getStatus());
        assertEquals(LocalDateTime.now().getMinute(), result.getDateTime().getMinute()); // Verifica apenas os minutos por simplicidade
    }

    @Test
    void indexTest() {


        List<Ticket> tickets = this.crateTicketList();
        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        // Test
        List<ResponseTicket> result = ticketService.index();

        // Assertions
        assertEquals(2, result.size());
    }

    @Test
    void showTest() {
        // Setup
        Ticket ticket = this.crateTicketList().get(0);
        ticket.setId(1L);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Test
        ResponseTicket result = ticketService.show(1L);

        // Assertions
        assertEquals(ticket.getId(), result.getId());
    }

    @Test
    void destroyTest() {
        // Setup
        Ticket ticket = new Ticket();
        ticket.setListResponse(new ArrayList<>());
        ticket.setListCategory(new ArrayList<>());
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Test
        String result = ticketService.destroy(1L);

        // Assertions
        assertEquals("Ticket deleted successfully", result);
        Mockito.verify(ticketRepository).deleteById(1L);
    }

    @Test
    void findByStatusTest() {
        // Setup
        List<Ticket> tickets = this.crateTicketList();
        Mockito.when(ticketRepository.ticketStatus(TicketStatus.OPEN.getValue())).thenReturn(Arrays.asList( tickets.get(0)));

        // Test
        List<TicketSimplified> result = ticketService.findByStatus(1);

        // Assertions
        assertEquals(1, result.size());
        for (TicketSimplified ticket: result) {
            assertEquals(TicketStatus.OPEN, ticket.getStatus());
        }
    }

    @Test
    void orderBydateTimedTest() {
        // Setup
        List<Ticket> tickets = this.crateTicketList();
        Mockito.when(ticketRepository.orderFindDateTime(1)).thenReturn(tickets);

        // Test
        List<TicketSimplified> result = ticketService.orderBydateTimed(1);

        // Assertions
        assertEquals(2, result.size());
    }

    List<Ticket> crateTicketList(){
        User user1 = new User();
        user1.setId(3L);  // Definindo o ID
        user1.setName("Mamonha");
        user1.setEmail("mamonha@gmail.com");
        user1.setContact("45 99999-4565");
        user1.setCpf("8018742939");
        user1.setType(1);


        Category category1 = new Category();
        category1.setId(1L);  // Definindo o ID
        category1.setNome("Categoria 1");
        category1.setDescription("Descrição da Categoria 1");

        Category category2 = new Category();
        category2.setId(2L);  // Definindo o ID
        category2.setNome("Categoria 2");
        category2.setDescription("Descrição da Categoria 2");

        Ticket ticket1 = new Ticket();
        ticket1.setId(101L);  // Definindo o ID
        ticket1.setTitle("Ticket 1");
        ticket1.setDescription("Descrição do Ticket 1");
        ticket1.setPriority(1);
        ticket1.setStatus(1);
        ticket1.setUser(user1);
        ticket1.setDateTime(LocalDateTime.now());

        Ticket ticket2 = new Ticket();
        ticket2.setId(102L);  // Definindo o ID
        ticket2.setTitle("Ticket 2");
        ticket2.setDescription("Descrição do Ticket 2");
        ticket2.setPriority(2);
        ticket2.setStatus(2);
        ticket2.setUser(user1);
        ticket2.setDateTime(LocalDateTime.now());

        ticket1.setListCategory(Arrays.asList(category1, category2));
        ticket2.setListCategory(Arrays.asList(category1));

        return  Arrays.asList(ticket1, ticket2);
    }
}