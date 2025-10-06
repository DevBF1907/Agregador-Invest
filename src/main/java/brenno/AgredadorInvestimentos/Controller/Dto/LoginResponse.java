package brenno.AgredadorInvestimentos.Controller.Dto;

public record LoginResponse(
    String token,
    String type,
    String userId,
    String username
) {}
