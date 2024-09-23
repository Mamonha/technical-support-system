package app.ticket;

import app.dto.Ticket.RequestTicket;
import app.dto.Ticket.ResponseTicket;
import app.dto.Ticket.TicketSimplified;
import app.entities.Category;
import app.entities.Ticket;
import app.entities.User;
import app.enums.TicketStatus;
import app.repositories.CategoryRepository;
import app.repositories.TicketRepository;
import app.repositories.UserRepository;
import app.services.ResponseService;
import app.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    @Test
//    void openTest() {
//        // Setup
//        RequestTicket requestTicket = new RequestTicket();
//        requestTicket.setUserId(1L);
//        requestTicket.setCategoryIds(List.of(1L));
//
//        // Criar um Ticket com os dados de RequestTicket
//        Ticket ticket = new Ticket(); // você pode adicionar mais dados ao ticket se necessário
//
//        // Mockar o retorno do método save do ticketRepository
//        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);
//
//        // Test
//        Ticket result = ticketService.open(requestTicket);
//
//        // Assertions
//        assertEquals(TicketStatus.OPEN.getValue(), result.getStatus());
//        assertEquals(LocalDateTime.now().getMinute(), result.getDateTime().getMinute()); // Verifica apenas os minutos por simplicidade
//    }
//
//    @Test
//    void indexTest() {
//        // Setup
//        List<Ticket> tickets = new ArrayList<>();
//        tickets.add(new Ticket());
//        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);
//
//        // Test
//        List<ResponseTicket> result = ticketService.index();
//
//        // Assertions
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void showTest() {
//        // Setup
//        Ticket ticket = new Ticket();
//        ticket.setId(1L);
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
//
//        // Test
//        ResponseTicket result = ticketService.show(1L);
//
//        // Assertions
//        assertEquals(ticket.getId(), result.getId());
//    }

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

//    @Test
//    void findByStatusTest() {
//        // Setup
//        List<Ticket> tickets = new ArrayList<>();
//        Ticket ticket = new Ticket();
//        tickets.add(ticket);
//        Mockito.when(ticketRepository.ticketStatus(1)).thenReturn(tickets);
//
//        // Test
//        List<TicketSimplified> result = ticketService.findByStatus(1);
//
//        // Assertions
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void orderBydateTimedTest() {
//        // Setup
//        List<Ticket> tickets = new ArrayList<>();
//        Ticket ticket = new Ticket();
//        tickets.add(ticket);
//        Mockito.when(ticketRepository.orderFindDateTime(1)).thenReturn(tickets);
//
//        // Test
//        List<TicketSimplified> result = ticketService.orderBydateTimed(1);
//
//        // Assertions
//        assertEquals(1, result.size());
//    }
}