package divEmpresas.dto.log;

import divEmpresas.entity.AtividadeLog;
import java.time.LocalDateTime;

public record LogResponseDTO(
        Long id,
        String acao,
        String recurso,
        Long recursoID,
        LocalDateTime dataHora,
        String enderecoIP
) {
    public static LogResponseDTO fromLog(AtividadeLog atividadeLog)
    {
        return new LogResponseDTO(atividadeLog.getId(), atividadeLog.getAcao(), atividadeLog.getRecurso(), atividadeLog.getRecursoId(), atividadeLog.getDataHora(),
                atividadeLog.getEnderecoIp());
    }
}
