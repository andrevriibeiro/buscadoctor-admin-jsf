package br.com.devdojo.projetoinicial.service.dataloader;

import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.persistence.model.Especialidade;
import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.persistence.model.dto.EspecialistaDTO;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Created by andreribeiro on 15/11/17.
 *
 */
public class EspecialistasConsultorioDataLoader {

    /**
     *
     * Carrega Lista de EspecialistasConsultorio Habilitado
     *
     * @EspecialistaConsultorio: Especialista, Consultorio, boolean trabalhaFeriado, boolean habilitado
     *
     * */
    private static Map<Consultorio, List<EspecialistaConsultorio>> loadEspecialistaConsultorioEnabled(Consultorio consultorio) {

        Map<Consultorio, List<EspecialistaConsultorio>> consultorioEspecialistaConsultorioMap = new LinkedHashMap<>(2);

        consultorioEspecialistaConsultorioMap.put(consultorio,
                EspecialistaConsultorioService.search(Collections.singletonList(consultorio.getId().toString())));

        return consultorioEspecialistaConsultorioMap;
    }

    /**
     *
     * Carrega Lista de Especialistas Habilitado
     *
     * @EspecialistaDTO: Especialista, Consultorio list<Especialidades>
     *
     * */
    public static List<EspecialistaDTO> loadEspecialistaEspecialidadesEnabled(Consultorio consultorio){
        List<String> consultoriosIds = new ArrayList<>();
        consultoriosIds.add(consultorio.getId().toString());

        List<EspecialistaDTO> especialistasEspecialidadesEnabled =
                EspecialistaConsultorioService.searchEspecialistaConsultoriosEnabled(consultoriosIds, true);

        return especialistasEspecialidadesEnabled;
    }

    /**
     *
     * @param consultorio
     * @return Lista de EspecialistaConsultorio Habilitados
     *
     * */
    public static List<EspecialistaConsultorio> getEspecialistaConsultorioEnabled(Consultorio consultorio){
        return loadEspecialistaConsultorioEnabled(consultorio).get(consultorio)
                .stream()
                .filter(EspecialistaConsultorio::getHabilitado).collect(Collectors.toList());
    }

    /**
     *
     * @param consultorio
     * @return Lista de EspecialistaConsultorio Desabilitados
     *
     * */
    public static List<EspecialistaConsultorio> getEspecialistaConsultorioDisabled(Consultorio consultorio) {
        return loadEspecialistaConsultorioEnabled(consultorio).get(consultorio)
                .stream()
                .filter(especialistaConsultorio -> !especialistaConsultorio.getHabilitado()).collect(Collectors.toList());
    }

    /**
     *
     * @param consultorio
     * @return Set de EspecialistaDTO Habilitados
     *
     * */
    public static Set<EspecialistaDTO> getEspecialistaConsultorioEspecialidadesEnabledSet(Consultorio consultorio) {
        return loadEspecialistaEspecialidadesEnabled(consultorio)
                .stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     *
     * @param consultorio
     * @return Set de Especialistas Habilitados
     *
     * */
    public static Set<Especialista> getEspecialistasEnabledSet(Consultorio consultorio) {
        return getEspecialistaConsultorioEnabled(consultorio)
                .stream()
                .map(EspecialistaConsultorio::getEspecialista)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     *
     * @param consultorio
     * @return Set de Especialidades dos Especialistas Habilitados
     *
     * */
    public static Set<Especialidade> getEspecialidadesEnabledSet(Consultorio consultorio) {
        return loadEspecialistaEspecialidadesEnabled(consultorio)
                .stream()
                .map(EspecialistaDTO::getEspecialidades)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
