package kz.edu.astanait.picland.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String access;
    private String username;
    private String refresh;
    private Long id;
}
